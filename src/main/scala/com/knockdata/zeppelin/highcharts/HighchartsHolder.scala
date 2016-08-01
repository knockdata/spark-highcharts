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

class HighchartsHolder(dataFrame: DataFrame) {
  private var _seriesCol: Option[String] = None

  private val colDefsBuffer = mutable.Buffer[(String, Any)]()
  private val drillsDefsBuffer = mutable.Buffer[mutable.Buffer[(String, Any)]]()
  private val optionsBuffer = mutable.Buffer[BaseModel]()

  private var currentDefs = colDefsBuffer

  def seriesCol(columnName: String): this.type = {
    _seriesCol = Some(columnName)
    this
  }

  def series(defs: (String, Any)*): this.type = {
    currentDefs ++= defs
    this
  }

  def options(seriesOption: BasePlotOptions): this.type = {
    currentDefs += "options" -> seriesOption
    this
  }

  def orderBy(column: Column): this.type = {
    currentDefs += "orderBy" -> column
    this
  }

  def drilldown(defs: (String, Any)*): this.type = {
    currentDefs = defs.toBuffer

    drillsDefsBuffer += currentDefs
    this
  }

  def chart(chart: Chart): this.type = {
    appendOptions(chart)
  }

  def credits(credits: Credits): this.type = {
    appendOptions(credits)
  }

  def data(data: Data): this.type = {
    appendOptions(data)
  }

  def exporting(exporting: Exporting): this.type = {
    appendOptions(exporting)
  }

  def labels(labels: Labels): this.type = {
    appendOptions(labels)
  }

  def legend(legend: Legend): this.type = {
    appendOptions(legend)
  }

  def navigation(navigation: Navigation): this.type = {
    appendOptions(navigation)
  }

  def noData(value: Any) =
    throw new Exception("does not support noData")

  def pane(pane: Pane): this.type = {
    appendOptions(pane)
  }

  def plotOptions(plotOptions: BasePlotOptions*): this.type = {
    plotOptions.foreach(appendOptions)
    this
  }

  def subtitle(subtitle: Subtitle): this.type = {
    appendOptions(subtitle)
  }

  def title(title: Title): this.type = {
    appendOptions(title)
  }

  def tooltip(tooltip: Tooltip): this.type = {
    appendOptions(tooltip)
  }

  def xAxis(xAxis: Axis): this.type = {
    appendOptions(xAxis)
  }

  def yAxis(yAxis: Axis): this.type = {
    appendOptions(yAxis)
  }

  private def appendOptions(options: BaseModel): this.type = {
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
