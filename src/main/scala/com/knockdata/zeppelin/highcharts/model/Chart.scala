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

  def alignTicks(value: Boolean): this.type = {
    append("alignTicks", value)
  }

  def animation(value: Boolean): this.type = {
    append("",value)
  }

  def backgroundColor(value: String): this.type = {
    append("backgroundColor",value)
  }

  def borderColor(value: String): this.type = {
    append("borderColor",value)
  }

  def borderRadius(value: Int): this.type = {
    append("borderRadius",value)
  }

  def borderWidth(value: Int): this.type = {
    append("borderWidth",value)
  }

  def className(value: String): this.type = {
    append("className",value)
  }

  def defaultSeriesType(value: String): this.type = {
    append("defaultSeriesType",value)
  }

  def events(values: (String, String)*): this.type = {
    append("events",values.map{
      case (fieldName, code) =>
        fieldName -> placeholdCode(code)
    }.toMap)
  }

  def height(value: Int): this.type = {
    append("height",value)
  }

  def ignoreHiddenSeries(value: Boolean): this.type = {
    append("ignoreHiddenSeries",value)
  }

  def inverted(value: Boolean): this.type = {
    append("inverted",value)
  }

  def option3d(values: (String, Any)*): this.type = {
    append("option3d",values.toMap)
  }

  def panKey(value: String): this.type = {
    append("panKey",value)
  }

  def panning(value: Boolean): this.type = {
    append("panning",value)
  }

  def pinchType(value: String): this.type = {
    append("pinchType",value)
  }

  def plotBackgroundColor(value: String): this.type = {
    append("plotBackgroundColor",value)
  }

  def plotBackgroundImage(value: String): this.type = {
    append("plotBackgroundImage",value)
  }

  def plotBorderColor(value: Boolean): this.type = {
    append("plotBorderColor",value)
  }

  def plotBorderWidth(value: Int): this.type = {
    append("plotBorderWidth",value)
  }

  def plotShadow(value: Any): this.type = {
    append("plotShadow", value)
  }

  def polar(value: Boolean): this.type = {
    append("polar", value)
  }

  def reflow(value: Boolean): this.type = {
    append("reflow", value)
  }

  // renderTo is defined by the plot method in Highcharts
  //  def renderTo(value: Boolean) =
  //    append("renderTo", value)

  def resetZoomButton(values: (String, Any)*): this.type = {
    append("resetZoomButton", values.toMap)
  }

  def selectionMarkerFill(value: String): this.type = {
    append("selectionMarkerFill", value)
  }

  def shadow(value: Boolean): this.type = {
    append("shadow", value)
  }

  def shadow(values: (String, Any)*): this.type = {
    append("shadow", values.toMap)
  }

  def showAxes(value: Boolean): this.type = {
    append("showAxes", value)
  }

  def style(values: (String, Any)*): this.type = {
    append("style", values.toMap)
  }

  def width(value: Int): this.type = {
    append("width", value)
  }

  def zoomType(value: String): this.type = {
    append("zoomType", value)
  }
}

object Chart{
  def apply(chartType: String): Chart = {
    new Chart(chartType)
  }
}
