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

import org.apache.spark.sql.{Column, DataFrame}

import base._
import model._

import scala.collection.mutable

private[highcharts] class HighchartsHolder(dataFrame: DataFrame) {
  private var _seriesCol: Option[String] = None

  private val colDefsBuffer = mutable.Buffer[(String, Any)]()
  private val drillsDefsBuffer = mutable.Buffer[mutable.Buffer[(String, Any)]]()
  private val optionsBuffer = mutable.Buffer[BaseModel]()

  private var currentDefs = colDefsBuffer

  def seriesCol(columnName: String) = {
    _seriesCol = Some(columnName)
    this
  }

  def series(defs: (String, Any)*) = {
    currentDefs ++= defs
    this
  }

  def options(seriesOption: BasePlotOptions) = {
    currentDefs += "options" -> seriesOption
    this
  }

  def orderBy(column: Column) = {
    currentDefs += "orderBy" -> column
    this
  }

  def drilldown(defs: (String, Any)*) = {
    currentDefs = defs.toBuffer

    drillsDefsBuffer += currentDefs
    this
  }

  def chart(chart: Chart) =
    appendOptions(chart)

  def credits(credits: Credits) =
    appendOptions(credits)

  def data(data: Data) =
    appendOptions(data)

  def exporting(exporting: Exporting) =
    appendOptions(exporting)

  def labels(labels: Labels) =
    appendOptions(labels)

  def legend(legend: Legend) =
    appendOptions(legend)

  def navigation(navigation: Navigation) =
    appendOptions(navigation)

  def noData(value: Any) {
    throw new Exception("does not support noData")
  }

  def pane(pane: Pane) =
    appendOptions(pane)

  def plotOptions(plotOptions: BasePlotOptions*) = {
    plotOptions.foreach(appendOptions)
    this
  }

  def subtitle(subtitle: Subtitle) =
    appendOptions(subtitle)

  def subtitle(subtitle: String) =
    appendOptions(Subtitle(subtitle))

  def title(title: Title) =
    appendOptions(title)

  def title(title: String) =
    appendOptions(Title(title))

  def tooltip(tooltip: Tooltip) =
    appendOptions(tooltip)

  def xAxis(xAxis: Axis) =
    appendOptions(xAxis)

  def yAxis(yAxis: Axis) =
    appendOptions(yAxis)

  private def appendOptions(options: BaseModel) = {
    optionsBuffer += options
    this
  }

  def plot() = {
    val colDefs = colDefsBuffer.toList
    val drillsDefs = drillsDefsBuffer.toList.map{
      case buffer => buffer.toList
    }

    val chart = _seriesCol match {
      case None =>
        convert(dataFrame, colDefs, drillsDefs:_*)
      case Some(seriesCol) =>
        convert(dataFrame, seriesCol, colDefs, drillsDefs:_*)
    }

    chart.options(optionsBuffer.toList:_*).plot()
  }
}
