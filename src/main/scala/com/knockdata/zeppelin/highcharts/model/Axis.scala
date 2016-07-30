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

import com.knockdata.zeppelin.highcharts.base.BaseModel

class Axis(text: String = "") extends BaseModel with PublicApply {
  override def fieldName: String = "axis"

  //  val title = new AxisTitle(text)

  override def preProcessResult(): Unit = {
    if (text != "") {
      append("title", "text", text)
    }
    super.preProcessResult
  }


  def typ(value: String): this.type = {
    append("type", value)
  }

  def allowDecimals(value: Boolean): this.type = {
    append("allowDecimals", value)
  }

  def alternateGridColor(value: String): this.type = {
    append("alternateGridColor", value)
  }

  def breaks(values: Map[String, Any]*): this.type = {
    append("breaks", values.toList)
  }

  def categories(values: String*): this.type = {
    append("categories", values.toList)
  }

  def ceiling(value: Int): this.type = {
    append("ceiling", value)
  }

  def crosshair(value: Boolean): this.type = {
    append("crosshair", value)
  }

  def dateTimeLabelFormats(values: (String, Any)*): this.type = {
    append("dateTimeLabelFormats", values.toMap)
  }

  def endOnTick(value: Boolean): this.type = {
    append("endOnTick", value)
  }

  def events(values: (String, String)*): this.type = {
    append("events", values.map {
      case (k, v) => k -> Code(v)
    })
  }

  def floor(value: Int): this.type = {
    append("ceiling", value)
  }

  def gridLineColor(value: String): this.type = {
    append("gridLineColor", value)
  }

  def gridLineDashStyle(value: String): this.type = {
    expect(value, "dash", "dot", "solid")
    append("gridLineDashStyle", value)
  }

  def gridLineWidth(value: Int): this.type = {
    append("gridLineWidth", value)
  }

  def gridZIndex(value: Int): this.type = {
    append("gridZIndex", value)
  }

  def id(value: String): this.type = {
    append("id", value)
  }

  def labels(values: (String, Any)*): this.type = {
    append("labels", values.map{
      case ("formatter", v: String) =>
        "formatter" -> placeholdCode(v)
      case ("formatter", v: Code) =>
        "formatter" -> placeholdCode(v)
      case (k, v) => k -> v
    }.toMap)
  }

  def lineColor(value: String): this.type = {
    append("lineColor", value)
  }

  def lineWidth(value: Int): this.type = {
    append("lineWidth", value)
  }

  def linkedTo(value: Int): this.type = {
    append("linkedTo", value)
  }

  def max(value: Double): this.type = {
    append("max", value)
  }

  def maxPadding(value: Double): this.type = {
    append("maxPadding", value)
  }

  def maxZoom(value: Double): this.type = {
    throw new Exception("using minRange instead, deprecated since Highcharts 2.2")
  }

  def min(value: Double): this.type = {
    append("min", value)
  }

  def minPadding(value: Double): this.type = {
    append("minPadding", value)
  }

  def minRange(value: Double): this.type = {
    append("minRange", value)
  }

  def minTickInterval(value: Double): this.type = {
    append("minTickInterval", value)
  }

  def minorGridLineColor(value: String): this.type = {
    append("minorGridLineColor", value)
  }

  def minorGridLineDashStyle(value: String): this.type = {
    append("minorGridLineDashStyle", value)
  }

  def minorGridLineWidth(value: Int): this.type = {
    append("minorGridLineWidth", value)
  }

  def minorTickColor(value: String): this.type = {
    append("minorTickColor", value)
  }

  def minorTickInterval(value: Double): this.type = {
    append("minorTickInterval", value)
  }

  def minorTickLength(value: Int): this.type = {
    append("minorTickLength", value)
  }

  def minorTickPosition(value: String): this.type = {
    append("minorTickPosition", value)
  }

  def minorTickWidth(value: String): this.type = {
    append("minorTickWidth", value)
  }

  def offset(value: Int): this.type = {
    append("offset", value)
  }

  def opposite(value: Boolean): this.type = {
    append("opposite", value)
  }

  def plotBands(values: Map[String, Any]*): this.type = {
    append("plotBands", values.toList)
  }

  def plotLines(values: Map[String, Any]*): this.type = {
    append("plotLines", values.toList)
  }

  def reversed(value: Boolean): this.type = {
    append("reversed", value)
  }

  def showEmpty(value: Boolean): this.type = {
    append("showEmpty", value)
  }

  def showFirstLabel(value: Boolean): this.type = {
    append("showFirstLabel", value)
  }

  def showLastLabel(value: String): this.type = {
    append("showLastLabel", value)
  }

  def startOfWeek(value: Int): this.type = {
    append("startOfWeek", value)
  }

  def startOnTick(value: Boolean): this.type = {
    append("startOnTick", value)
  }

  def tickAmount(value: Int): this.type = {
    append("tickAmount", value)
  }

  def tickColor(value: String): this.type = {
    append("tickColor", value)
  }

  def tickInterval(value: Double): this.type = {
    append("tickInterval", value)
  }

  def tickLength(value: Int): this.type = {
    append("tickLength", value)
  }

  def tickPixelInterval(value: Int): this.type = {
    append("tickPixelInterval", value)
  }

  def tickPosition(value: String): this.type = {
    expect(value, "inside", "outside")
    append("tickPosition", value)
  }

  def tickPositioner(code: String): this.type = {
    append("tickPositioner", placeholdCode(code))
  }

  def tickPositions(values: Int*): this.type = {
    append("tickPositions", values.toList)
  }

  def tickWidth(value: Int): this.type = {
    append("tickWidth", value)
  }

  def tickmarkPlacement(value: String): this.type = {
    append("tickmarkPlacement", value)
  }

  /**
    * specify units
    *
    * @param values
    *               List("millisecond", List(1, 2, 5, 10, 20, 25, 50, 100, 200, 500)),
    *               List("second", List(1, 2, 5, 10, 15, 30))
    * @return this
    */
  def units(values: List[Any]*): this.type = {
    append("units", values.toList)
  }

  def visible(value: Boolean): this.type = {
    append("units", value)
  }

}

class XAxis(text: String = "") extends Axis(text) {

  override def fieldName: String = "xAxis"
}

class YAxis(text: String = "") extends Axis(text) {
  override def fieldName: String = "yAxis"

  def gridLineInterpolation(value: String): this.type = {
    append("gridLineInterpolation", value)
  }

  def reversedStacks(value: Boolean): this.type = {
    append("reversedStacks", value)
  }

  def stackLabels(values: (String, Any)*): this.type = {
    append("stackLabels", values.map{
      case ("formatter", v: String) => "formatter" -> Code(v)
      case (k, v) => k -> v
    }.toMap)
  }

  def stops(values: List[Any]*): this.type = {
    append("stops", values.toList)
  }
}


