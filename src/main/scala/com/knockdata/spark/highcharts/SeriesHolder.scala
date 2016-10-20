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

package com.knockdata.spark.highcharts

import com.knockdata.spark.highcharts.model._
import org.apache.spark.sql.{Column, DataFrame, Row}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag


private[highcharts] object SeriesHolder {
  type Series2Series = Series => Series
  type DataFrame2DataFrame = DataFrame => DataFrame
  type DataFrame2ArrayRow = DataFrame => Array[Row]
  type ArrayRow2ArrayRow = Array[Row] => Array[Row]

  case class Defs(nameDefs: List[(String, String)],
                  aggDefs: List[(String, Column)],
                  s2sDefs: List[Series2Series],
                  df2dfDefs: List[DataFrame2DataFrame],
                  df2ar: DataFrame2ArrayRow,
                  orderByCols: List[Column],
                  ar2arDefs: List[ArrayRow2ArrayRow]) {

    def getRows(dataFrame: DataFrame): Array[Row] = {
      df2ar(dataFrame)
    }

    lazy val nameCols: List[String] = nameDefs.map(_._2)
    lazy val aggCols: List[String] = aggDefs.map(_._2.toString())

    lazy val wantedCol: List[String] = nameCols ::: aggCols

    lazy val allColsMap = nameDefs ::: aggDefs.map(t => (t._1, t._2.toString()))

  }

}

private[highcharts] class SeriesHolder(data: DataFrame) {
  import SeriesHolder._

  var dataFrame: DataFrame = data

  val chartId: String = id

  private var nameDefBuffer = new ListBuffer[(String, String)]
  private var aggDefBuffer = new ListBuffer[(String, Column)]

  private var s2sBuffer = new ListBuffer[Series2Series]()
  private var df2dfBuffer = new ListBuffer[DataFrame2DataFrame]()
  private var df2arBuffer = new ListBuffer[DataFrame2ArrayRow]()
  private var ar2arBuffer = new ListBuffer[ArrayRow2ArrayRow]()
  private var orderByColBuffer = new ListBuffer[Column]()

  var _seriesCol: Option[String] = None

  def seriesCol(columnName: String): this.type = {
    _seriesCol = Some(columnName)
    this
  }

  private val defsBuffer = mutable.Buffer[Defs]()

  def set(name: String, value: Any): this.type = {
     s2sBuffer += ((s: Series) => s(name, value))
    this
  }

  def set(name: String, subName: String, value: Any): this.type = {
     s2sBuffer += ((s: Series) => s(name, subName, value))
    this
  }

  def ops(ops: (DataFrame => DataFrame)*): this.type = {
     df2dfBuffer ++= ops
    this
  }

  def ops[X: ClassTag](ops: (Array[Row] => Array[Row])*): this.type = {
     ar2arBuffer ++= ops
    this
  }

  def series(defs: (String, Any)*): this.type = {
    defs.foreach{
      case (jsonFieldName: String, columnName: String) =>
         nameDefBuffer += jsonFieldName -> columnName
      case (jsonFieldName: String, column: Column) =>
         aggDefBuffer += jsonFieldName -> column
    }
    this
  }

  def orderBy(columns: Column*): this.type = {
    orderByColBuffer ++= columns
    df2dfBuffer += ((df: DataFrame) => df.orderBy(columns:_*))
    this
  }

  // always using without replacement, can not specify seed
  // https://www.ma.utexas.edu/users/parker/sampling/repl.htm
  def sample(fractions: Double): this.type = {
     df2dfBuffer += ((df: DataFrame) => df.sample(false, fractions))

    this
  }

  def take(n: Int): this.type = {
    df2arBuffer += ((df: DataFrame) => df.take(n))

    this
  }

  def getDef(): Defs = {
    val df2ar =
      if (df2arBuffer.isEmpty)
        (dataFrame: DataFrame) => dataFrame.collect()
      else if (df2arBuffer.size == 1)
        df2arBuffer.head
      else
        throw new Exception("It should only have one DataFrame2DataFrame options")

    Defs(nameDefBuffer.toList,
      aggDefBuffer.toList,
      s2sBuffer.toList,
      df2dfBuffer.toList,
      df2ar,
      orderByColBuffer.toList,
      ar2arBuffer.toList)
  }

  def drilldown(defs: (String, Any)*): this.type = {
    defsBuffer += getDef()


    nameDefBuffer = new ListBuffer[(String, String)]
    aggDefBuffer = new ListBuffer[(String, Column)]

    s2sBuffer = new ListBuffer[Series2Series]()
    df2dfBuffer = new ListBuffer[DataFrame2DataFrame]()
    df2arBuffer = new ListBuffer[DataFrame2ArrayRow]()
    ar2arBuffer = new ListBuffer[ArrayRow2ArrayRow]()
    orderByColBuffer = new ListBuffer[Column]()

    defs.foreach{
      case (jsonFieldName: String, columnName: String) =>
         nameDefBuffer += jsonFieldName -> columnName
      case (jsonFieldName: String, column: Column) =>
         aggDefBuffer += jsonFieldName -> column
    }

    this
  }

  def result: (List[Series], List[Series]) = {
    defsBuffer += getDef()

    _seriesCol match {
      case None =>
        convert(dataFrame, defsBuffer.toList)
      case Some(seriesCol) =>
        convert(dataFrame, seriesCol, defsBuffer.toList)
    }
  }
}

//class SeriesHolder(df: DataFrame) extends AbstractSeriesHolder {
//  def dataFrame: DataFrame = df
//}
//
//class StreamingSeriesHolder(df: DataFrame) extends AbstractSeriesHolder {
//  var dataFrame: DataFrame = df
//}
