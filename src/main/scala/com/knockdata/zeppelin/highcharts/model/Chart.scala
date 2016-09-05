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

package com.knockdata.zeppelin.highcharts.model

import com.knockdata.zeppelin.highcharts.base._

class Chart(chartType: String) extends BaseModel
  with Margin
  with Spacing
  with PublicApply
{
  override def fieldName: String = "chart"
  append("type", chartType)

  def alignTicks(value: Boolean) =
    append("alignTicks", value)

  def animation(value: Boolean) =
    append("", value)

  def backgroundColor(value: String) =
    append("backgroundColor", value)

  def borderColor(value: String) =
    append("borderColor", value)

  def borderRadius(value: Int) =
    append("borderRadius", value)

  def borderWidth(value: Int) =
    append("borderWidth", value)

  def className(value: String) =
    append("className", value)

  def defaultSeriesType(value: String) =
    append("defaultSeriesType", value)

  def events(values: (String, String)*) =
    append("events", values.map {
      case (fieldName, code) =>
        fieldName -> placeholdCode(code)
    }.toMap)

  def height(value: Int) =
    append("height", value)

  def ignoreHiddenSeries(value: Boolean) =
    append("ignoreHiddenSeries", value)

  def inverted(value: Boolean) =
    append("inverted", value)

  def option3d(values: (String, Any)*) =
    append("option3d", values.toMap)

  def panKey(value: String) =
    append("panKey", value)

  def panning(value: Boolean) =
    append("panning", value)

  def pinchType(value: String) =
    append("pinchType", value)

  def plotBackgroundColor(value: String) =
    append("plotBackgroundColor", value)

  def plotBackgroundImage(value: String) =
    append("plotBackgroundImage", value)

  def plotBorderColor(value: Boolean) =
    append("plotBorderColor", value)

  def plotBorderWidth(value: Int) =
    append("plotBorderWidth", value)

  def plotShadow(value: Any) =
    append("plotShadow", value)

  def polar(value: Boolean) =
    append("polar", value)

  def reflow(value: Boolean) =
    append("reflow", value)

  // renderTo is defined by the plot method in Highcharts
  //  def renderTo(value: Boolean) =
  //    append("renderTo", value)

  def resetZoomButton(values: (String, Any)*) =
    append("resetZoomButton", values.toMap)

  def selectionMarkerFill(value: String) =
    append("selectionMarkerFill", value)

  def shadow(value: Boolean) =
    append("shadow", value)

  def shadow(values: (String, Any)*) =
    append("shadow", values.toMap)

  def showAxes(value: Boolean) =
    append("showAxes", value)

  def style(values: (String, Any)*) =
    append("style", values.toMap)

  def width(value: Int) =
    append("width", value)

  def zoomType(value: String) =
    append("zoomType", value)
}

object Chart {
  def apply(chartType: String) = new Chart(chartType)

  def area = new Chart("area")

  def arearange = new Chart("arearange")

  def areaspline = new Chart("areaspline")

  def areasplinerange = new Chart("areasplinerange")

  def bar = new Chart("bar")

  def boxplot = new Chart("boxplot")

  def bubble = new Chart("bubble")

  def column = new Chart("column")

  def columnrange = new Chart("columnrange")

  def errorbar = new Chart("errorbar")

  def funnel = new Chart("funnel")

  def gauge = new Chart("gauge")

  def heatmap = new Chart("heatmap")

  def line = new Chart("line")

  def pie = new Chart("pie")

  def polygon = new Chart("polygon")

  def pyramid = new Chart("pyramid")

  def scatter = new Chart("scatter")

  def solidgauge = new Chart("solidgauge")

  def spline = new Chart("spline")

  def treemap = new Chart("treemap")

  def waterfall = new Chart("waterfall")
}
