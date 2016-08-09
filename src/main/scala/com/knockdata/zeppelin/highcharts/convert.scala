/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.knockdata.zeppelin.highcharts

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, DataFrame, Row}

import scala.collection.mutable

import model._
import SeriesHolder.Defs

object convert {
  def getFieldColumnMap(defs: Defs, withDrilldownField: Boolean): List[(String, String)] = {

    if (withDrilldownField)
      ("drilldown" -> "drilldown") :: defs.allColsMap
    else
      defs.allColsMap
  }

  /**
    * get data from dataFrame with drill definition
    * using groupBy and agg
    *
    * @param dataFrame
    * @param defs (jsonFieldName -> columnName) or
    *                (jsonField -> Column) the Column is with agg function
    *                ("orderBy" -> Column)
    * @return (columns, rows)
    */
  private[zeppelin] def getRows(dataFrame: DataFrame,
                                currentKeys: List[String],
                                defs: Defs,
                                withDrilldownField: Boolean): Array[Row] = {
    val dfAgg =
      if (defs.aggDefs.isEmpty || defs.nameDefs.isEmpty) {
        dataFrame
      }
      else {
        val groupByCols = defs.nameDefs.map {
          case (jsonFieldName, colName: String) =>
            col(colName)
        }

        val aggCols = defs.aggDefs.map {
          case (jsonFieldName, aggCol: Column) =>
            aggCol
        }

        dataFrame
          .groupBy(groupByCols: _*)
          .agg(aggCols.head, aggCols.tail: _*)
      }

    val dfCustomized: DataFrame = (dfAgg /: defs.df2dfDefs)((temp: DataFrame, df2df) => df2df(temp))


    val wantedCols = defs.wantedCol
    val dfSelected = dfCustomized.select(wantedCols.head, wantedCols.tail:_*)

    val dfDrill =
      if (withDrilldownField)
        withDrilldown(currentKeys, defs.nameDefs, dfSelected)
      else
        dfSelected

    val rows: Array[Row] = defs.getRows(dfDrill)

    val rowsCustomized = (rows /: defs.ar2arDefs)((ar: Array[Row], ar2ar) => ar2ar(ar))

    rowsCustomized
  }

  private[zeppelin] def withDrilldown(currentKeys: List[String],
                                      colNameDefs: List[(String, Any)],
                                      aggDF: DataFrame): DataFrame = {
    val previousDrillField =
      if (currentKeys.isEmpty)
        ""
      else
        currentKeys.mkString(",") + ","

    colNameDefs match {
      // groupBy one column
      case (firstJsonFieldName, firstColName: String) :: Nil =>
        val udfDrilldown1 = udf[String, String] {
          (v1: String) => s"$previousDrillField$firstColName=$v1"
        }
        aggDF.withColumn("drilldown",
          udfDrilldown1(col(firstColName)))

      // groupBy two columns
      case (firstJsonFieldName, firstColName: String)
        :: (secondJsonFieldName, secondColName: String)
        :: Nil =>
        val udfDrilldown2 = udf[String, String, String] {
          (v1: String, v2: String) => s"$previousDrillField$firstColName=$v1,$secondColName=$v2"
        }
        aggDF.withColumn("drilldown",
          udfDrilldown2(col(firstColName), col(secondColName)))

      case _ =>
        throw new Exception("Only groupBy 1 or 2 columns are supported")
    }
  }

  /**
    * From the current dataFrame and what data is now
    * get the next level DataFrames wanna drilldown
    *
    * @param currentDataFrame the current dataFrame
    * @param row              the current row for the data point.
    *                         highchart will drilldown to next level when click it.
    * @param groupByColumns   (jsonFieldName -> columnName)
    * @return (keys, nextDataFrame)
    *         example: (List("marital=single", "job=entrepreneur"), nextDataFrame)
    */
  private[zeppelin] def nextDFs(currentDataFrame: DataFrame,
                                row: Row,
                                groupByColumns: List[(String, String)]): (List[String], DataFrame) = {
    val keys = groupByColumns.map {
      case (jsonFieldName, columnName) =>
        // the original column has been alias to jsonFieldName
        // for easy convert to map data point
        val colVal = row.getAs[Any](columnName)
        s"$columnName=$colVal"
    }

    // groupBy columns does not need for next level drilldown
    // for each level of drill down, columns getting less and less
    // e.g the original columns are
    //     marital, job, education, balance
    // if we drilldown marital, then the next level only need
    //              job, education, balance
    // if we drilldown job, then the next level only need
    //                   education, balance
    val wantedCols = currentDataFrame.columns.toList diff groupByColumns

    // in case of there are multiple groupByColumns
    // filter one groupBy column a time
    // start from currentDataFrame
    val nextDF = (currentDataFrame /: groupByColumns) (
      (df: DataFrame, pair: (String, String)) => {
        val (_, colName) = pair
        df.filter(col(colName) === row.getAs[Any](colName))
      }
    ).selectExpr(wantedCols: _*)

    (keys, nextDF)
  }

  private case class Data(keys: List[String],
                          withDrilldownField: Boolean,
                          defs: Defs,
                          rows: Array[Row]) {
    var _name: String = if (keys.isEmpty) "" else keys.last

    def setName(name: Any): Data = {
      _name = name.toString
      this
    }

    def name: String = _name
  }

  /**
    * drilldown one level at time from currentDataFrame with a recursive call.
    * All drilldown data store in the buffer
    *
    * @param currentDataFrame
    * @param allDefs      List(List(jsonFieldName, groupByColumnName/aggColumn))
    *                    the number of left drills will decrease for each level drilldown
    *                    when it's empty, it does not need be drilldown any further
    * @param currentKeys Nil for root.
    *                    The number of keys will increase for each level drilldown
    * @param buffer      to store the drilldown data
    */
  private def drilldown(currentDataFrame: DataFrame,
                        allDefs: List[Defs],
                        currentKeys: List[String],
                        buffer: mutable.ListBuffer[Data]): Unit = {
    val currentDrilldown :: restDrills = allDefs
    val withDrilldownField = restDrills.nonEmpty

    val rows = getRows(currentDataFrame, currentKeys, currentDrilldown, withDrilldownField)

    buffer += Data(currentKeys, withDrilldownField, currentDrilldown, rows)

    // drilldown if there are more levels
    if (restDrills.nonEmpty) {
      //      val drillColumns = currentDrilldown.filter(_._2.isInstanceOf[String])
      //        .map(pair => (pair._1, pair._2.asInstanceOf[String]))

      // only collect if it's groupByColumnName
      val drillColumns = currentDrilldown.nameDefs

      // for each row, we need drilldown once (create one more data series)
      rows.foreach {
        row =>
          val (nextKeys, nextDF) = nextDFs(currentDataFrame, row, drillColumns)
          val fullNextKeys: List[String] = currentKeys ::: nextKeys

          drilldown(nextDF,
            restDrills,
            fullNextKeys,
            buffer)
      }
    }
  }

  /**
    * create a drilldown highchart with the rootDataFrame
    *
    * @param rootDataFrame
    * @param allDefs List((jsonFieldName, groupByColumnName/aggColumn))*
    * @return highchart
    */
  def apply(rootDataFrame: DataFrame,
            allDefs: List[Defs]): (List[Series], List[Series]) = {
    val buffer = mutable.ListBuffer[Data]()

    drilldown(rootDataFrame, allDefs, Nil, buffer)

    val normalData :: drilldownData = buffer.result()
    val normalSeries = toSeries(normalData)

    val drilldownSeriesList =
      drilldownData match {
        case Nil =>
          Nil
        case xs =>
          toSeriesList(drilldownData)
      }

    (List(normalSeries), drilldownSeriesList)
  }

  def getCategories(rootDataFrame: DataFrame,
                    defs: Defs): List[(String, Map[String, Any])] = {

    // if there is only one column, then no category needed
    if ((defs.nameDefs.size + defs.aggDefs.size) == 1) {
      Nil
    }
    else {
      val dfOrdered =
        if (defs.orderByCols.isEmpty) {
          rootDataFrame
        } else {
          rootDataFrame.orderBy(defs.orderByCols: _*)
        }

      val (categoryFieldName, categoryCol) = defs.nameDefs.head

      val rows = dfOrdered.selectExpr(categoryCol).distinct.collect.toList


      val nulls: Map[String, Any] = (defs.nameDefs.tail ++ defs.aggDefs).map {
        case (jsonFieldName, column) =>
          jsonFieldName -> null
      }.toMap


      rows.map {
        row =>
          val category = row.get(0)
          val defaultValues = nulls.updated(categoryFieldName, category)
          category.toString -> defaultValues
      }
    }
  }

  def apply(rootDataFrame: DataFrame,
            seriesCol: String,
            allDefs: List[Defs]): (List[Series], List[Series]) = {

    val allSeriesValues = rootDataFrame.select(seriesCol).distinct.orderBy(col(seriesCol)).collect.map(_.get(0))

    val categories = getCategories(rootDataFrame, allDefs.head)

    val column = col(seriesCol)
    val wantedCols = rootDataFrame.columns.filter(_ != seriesCol)

    val bufferNormalSeries = mutable.ListBuffer[Data]()
    val bufferDrilldownSeries = mutable.ListBuffer[Data]()


    for (aSeriesValue <- allSeriesValues) {
      val seriesDataFrame =
        rootDataFrame.filter(column === aSeriesValue).selectExpr(wantedCols: _*)

      val keys = s"$seriesCol=${aSeriesValue.toString}" :: Nil
      val buffer = mutable.ListBuffer[Data]()

      drilldown(seriesDataFrame, allDefs, keys, buffer)

      val normalSeries :: drilldownSeries = buffer.result()

      bufferNormalSeries += normalSeries.setName(aSeriesValue)

      bufferDrilldownSeries ++= drilldownSeries

    }

    val normalSeriesList = toSeriesList(bufferNormalSeries.result(), categories)

    val drilldownSeriesList = toSeriesList(bufferDrilldownSeries.result())

    (normalSeriesList, drilldownSeriesList)
  }


  private def toSeries(data: Data): Series = {

    val colsMap =
      if (data.withDrilldownField)
        "drilldown" -> "drilldown" :: data.defs.allColsMap
      else
        data.defs.allColsMap

    val mapData: Array[Map[String, Any]] = data.rows.map {
      row =>
        colsMap.map {
          case (jsonFieldName, columnName) =>
            jsonFieldName -> row.getAs[Any](columnName)
        }.toMap
    }

    val series =
      new Series(mapData.toList)
        .id(data.keys.mkString(","))
        .name(data.name)

    (series /: data.defs.s2sDefs)((s: Series, s2s) => s2s(s))
  }

  def getNameRows(data: Data): Map[String, Row] = {
    data.rows.map {
      row =>
        val category = row.get(0).toString
        (category, row)
    }.toMap
  }

  private def toSeries(data: Data, categories: List[(String, Map[String, Any])]): Series = {

    val nameRows = getNameRows(data)

    val colsMap =
      if (data.withDrilldownField)
        "drilldown" -> "drilldown" :: data.defs.allColsMap
      else
        data.defs.allColsMap

    val mapData: List[Map[String, Any]] =
      categories.map {
        case (category, defaultValues) =>
          if (nameRows.contains(category)) {
            val row = nameRows(category)
            colsMap.map {
              case (jsonFieldName, columnName) =>
                jsonFieldName -> row.getAs[Any](columnName)
            }.toMap
          }
          else {
            defaultValues
          }
      }


    val series = new Series(mapData)
        .id(data.keys.mkString(","))
        .name(data.name)

    (series /: data.defs.s2sDefs)((s: Series, s2s) => s2s(s))
  }

  /**
    * convert data to series
    *
    * @param allData
    * @return
    */
  private def toSeriesList(allData: List[Data]): List[Series] = {
    val allSeries = allData.map(toSeries)

    allSeries
  }

  private def toSeriesList(allData: List[Data], categories: List[(String, Map[String, Any])]): List[Series] = {
    val allSeries = allData.map {
      categories match {
        case Nil =>
          toSeries
        case xs =>
          toSeries(_, categories)
      }

    }

    allSeries
  }
}
