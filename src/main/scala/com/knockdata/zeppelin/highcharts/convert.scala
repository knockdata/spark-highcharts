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

import scala.collection.mutable.ListBuffer

object convert {
  var seriesOptions = Set("chart.type")
  val chartOptions = Set("xAxis")
  val optionFields = Set("orderBy", "sortBy") ++ chartOptions ++ seriesOptions

  def setSeriesOptions(series: Series, colDefs: List[(String, Any)]): Series = {
    val options = colDefs.collect {
      case (name, value: String) if seriesOptions.contains(name) =>
        (name, value)
    }

    (series /: options) ((s: Series, pair: (String, String)) => {
      val (optionName, optionValue) = pair
      optionName.split('.').toList match {
        case fieldName :: Nil =>
          s(fieldName, optionValue)
        case fieldName :: subFieldName :: Nil =>
          s(fieldName, subFieldName, optionValue)
        case _ =>
          throw new Exception("it can only have two levels option")
      }
    })
  }

  def excludeOptions(colDefs: List[(String, Any)]): List[(String, Any)] = {
    colDefs.filter {
      pair =>
        !optionFields.contains(pair._1)
    }
  }

  def getFieldColumnMap(colDefs: List[(String, Any)], withDrilldownField: Boolean): List[(String, String)] = {
    val optionExcluded = excludeOptions(colDefs)

    val providedMap = optionExcluded.map { case (fieldName, column) => fieldName -> column.toString }
    if (withDrilldownField)
      ("drilldown" -> "drilldown") :: providedMap
    else
      providedMap
  }

  def getWantedColumns(colDefs: List[(String, Any)]): List[String] = {
    val optionExcluded = colDefs.filter {
      pair =>
        !optionFields.contains(pair._1)
    }

    optionExcluded.map { case (fieldName, column) => column.toString }
  }

  def partitionDefs(colDefs: List[(String, Any)]) = {
    val nameDefBuffer = new ListBuffer[(String, String)]
    val aggDefBuffer = new ListBuffer[(String, Column)]
    val optionDefBuffer = new ListBuffer[(String, Any)]

    for ((fieldName, theDef) <- colDefs) {
      if (optionFields.contains(fieldName)) {
        optionDefBuffer += fieldName -> theDef
      }
      else theDef match {
        case s: String =>
          nameDefBuffer += fieldName -> s
        case _ =>
          aggDefBuffer += fieldName -> theDef.asInstanceOf[Column]
      }

    }

    (nameDefBuffer.result(),
      aggDefBuffer.result(),
      optionDefBuffer.result())
  }

  /**
    * get data from dataFrame with drill definition
    * using groupBy and agg
    *
    * @param dataFrame
    * @param colDefs (jsonFieldName -> columnName) or
    *                (jsonField -> Column) the Column is with agg function
    *                ("orderBy" -> Column)
    * @return (columns, rows)
    */
  private[zeppelin] def getRows(dataFrame: DataFrame,
                                currentKeys: List[String],
                                colDefs: List[(String, Any)],
                                withDrilldownField: Boolean): (Array[String], Array[Row]) = {
    val (colNameDefs, colAggDefs, optionDefs) = partitionDefs(colDefs)
    val orderByDefs: List[Column] = optionDefs.collect { case ("orderBy", column: Column) => column }

    val wantedCols = getWantedColumns(colDefs)

    val dfAgg =
      if (colAggDefs.isEmpty || colNameDefs.isEmpty) {
        dataFrame
      }
      else {
        val groupByCols = colNameDefs.map {
          case (jsonFieldName, colName: String) =>
            col(colName)
        }


        val aggCols = colAggDefs.map {
          case (jsonFieldName, aggCol: Column) =>
            aggCol
        }

        dataFrame
          .groupBy(groupByCols: _*)
          .agg(aggCols.head, aggCols.tail: _*)
      }

    val dfSelected = dfAgg.select(wantedCols.head, wantedCols.tail: _*)

    val dfOrdered =
      if (orderByDefs.isEmpty) {
        dfSelected
      } else {
        dfSelected.orderBy(orderByDefs: _*)
      }

    val dfDrill =
      if (withDrilldownField)
        withDrilldown(currentKeys, colNameDefs, dfOrdered)
      else
        dfOrdered

    (dfDrill.columns, dfDrill.collect())
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
                          colDefs: List[(String, String)],
                          allDefs: List[(String, Any)],
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
    * @param drills      List(List(jsonFieldName, groupByColumnName/aggColumn))
    *                    the number of left drills will decrease for each level drilldown
    *                    when it's empty, it does not need be drilldown any further
    * @param currentKeys Nil for root.
    *                    The number of keys will increase for each level drilldown
    * @param buffer      to store the drilldown data
    */
  private def drilldown(currentDataFrame: DataFrame,
                        drills: List[List[(String, Any)]],
                        currentKeys: List[String],
                        buffer: mutable.ListBuffer[Data]): Unit = {
    val currentDrilldown :: restDrills = drills
    val withDrilldownField = restDrills.nonEmpty

    val (columns, rows) = getRows(currentDataFrame, currentKeys, currentDrilldown, withDrilldownField)

    val fieldColumnMap = getFieldColumnMap(currentDrilldown, withDrilldownField)
    buffer += Data(currentKeys, fieldColumnMap, currentDrilldown, rows)

    // drilldown if there are more levels
    if (restDrills.nonEmpty) {
      //      val drillColumns = currentDrilldown.filter(_._2.isInstanceOf[String])
      //        .map(pair => (pair._1, pair._2.asInstanceOf[String]))

      // only collect if it's groupByColumnName
      val drillColumns = excludeOptions(currentDrilldown).collect {
        case (jsonFieldName, groupByColumnName: String) =>
          (jsonFieldName, groupByColumnName)
      }

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
    * @param colDefs List((jsonFieldName, groupByColumnName/aggColumn))*
    * @return highchart
    */
  def apply(rootDataFrame: DataFrame,
            colDefs: List[(String, Any)]): Series = {
    val buffer = mutable.ListBuffer[Data]()

    drilldown(rootDataFrame, colDefs :: Nil, Nil, buffer)

    // it only has one data
    val normalData = buffer.result().head

    toSeries(normalData)
  }

  def apply(rootDataFrame: DataFrame,
            colDefs: List[(String, Any)],
            drillDefsList: List[List[(String, Any)]]): (List[Series], List[Series]) = {
    val buffer = mutable.ListBuffer[Data]()


    drilldown(rootDataFrame, colDefs :: drillDefsList, Nil, buffer)

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
                    colDefs: List[(String, Any)]): List[(String, Map[String, Any])] = {

    val (colNameDefs, colAggDefs, optionDefs) = partitionDefs(colDefs)

    // if there is only one column, then no category needed
    if ((colNameDefs.size + colAggDefs.size) == 1) {
      Nil
    }
    else {
      val orderByDefs: List[Column] = optionDefs.collect { case ("orderBy", column: Column) => column }

      val dfOrdered =
        if (orderByDefs.isEmpty) {
          rootDataFrame
        } else {
          rootDataFrame.orderBy(orderByDefs: _*)
        }

      val (categoryFieldName, categoryCol) = colNameDefs.head

      val rows = dfOrdered.selectExpr(categoryCol).distinct.collect.toList


      val nulls: Map[String, Any] = (colNameDefs.tail ++ colAggDefs).map {
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
            colDefs: List[(String, Any)]): List[Series] = {

    val allSeriesValues = rootDataFrame.select(seriesCol).distinct.orderBy(col(seriesCol)).collect.map(_.get(0))

    val categories = getCategories(rootDataFrame, colDefs)

    val column = col(seriesCol)
    val wantedCols = rootDataFrame.columns.filter(_ != seriesCol)

    val bufferNormalSeries = mutable.ListBuffer[Data]()

    val drills = colDefs :: Nil
    for (aSeriesValue <- allSeriesValues) {
      val seriesDataFrame =
        rootDataFrame.filter(column === aSeriesValue).selectExpr(wantedCols: _*)

      val keys = s"$seriesCol=${aSeriesValue.toString}" :: Nil
      val buffer = mutable.ListBuffer[Data]()

      drilldown(seriesDataFrame, drills, keys, buffer)

      val normalSeries = buffer.result().head

      bufferNormalSeries += normalSeries.setName(aSeriesValue)
    }

    val normalSeriesList = toSeriesList(bufferNormalSeries.result(), categories)
    normalSeriesList
  }


  def apply(rootDataFrame: DataFrame,
            seriesCol: String,
            colDefs: List[(String, Any)],
            drillDefsList: List[List[(String, Any)]]): (List[Series], List[Series]) = {

    val allSeriesValues = rootDataFrame.select(seriesCol).distinct.orderBy(col(seriesCol)).collect.map(_.get(0))

    val categories = getCategories(rootDataFrame, colDefs)

    val column = col(seriesCol)
    val wantedCols = rootDataFrame.columns.filter(_ != seriesCol)

    val bufferNormalSeries = mutable.ListBuffer[Data]()
    val bufferDrilldownSeries = mutable.ListBuffer[Data]()

    val allDefs = colDefs :: drillDefsList

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
    val mapData: Array[Map[String, Any]] = data.rows.map {
      row =>
        data.colDefs.map {
          case (jsonFieldName, columnName) =>
            jsonFieldName -> row.getAs[Any](columnName)
        }.toMap
    }

    setSeriesOptions(
      new Series(mapData.toList)
        .id(data.keys.mkString(","))
        .name(data.name),
      data.allDefs
    )
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

    val mapData: List[Map[String, Any]] =
      categories.map {
        case (category, defaultValues) =>
          if (nameRows.contains(category)) {
            val row = nameRows(category)
            data.colDefs.map {
              case (jsonFieldName, columnName) =>
                jsonFieldName -> row.getAs[Any](columnName)
            }.toMap
          }
          else {
            defaultValues
          }
      }


    setSeriesOptions(
      new Series(mapData)
        .id(data.keys.mkString(","))
        .name(data.name),
      data.allDefs
    )
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
