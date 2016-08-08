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

  override def preProcessResult(): Unit = {
    if (text != "") {
      append("title", "text", text)
    }
    super.preProcessResult()
  }

  def typ(value: String) = {
    append("type", value)
  }

  def allowDecimals(value: Boolean) = {
    append("allowDecimals", value)
  }

  def alternateGridColor(value: String) = {
    append("alternateGridColor", value)
  }

  def breaks(values: Map[String, Any]*) = {
    append("breaks", values.toList)
  }

  def categories(values: String*) = {
    append("categories", values.toList)
  }

  def categories(values: List[String]) = {
    append("categories", values)
  }

  def ceiling(value: Int) = {
    append("ceiling", value)
  }

  def crosshair(value: Boolean) = {
    append("crosshair", value)
  }

  def dateTimeLabelFormats(values: (String, Any)*) = {
    append("dateTimeLabelFormats", values.toMap)
  }

  def endOnTick(value: Boolean) = {
    append("endOnTick", value)
  }

  def events(values: (String, String)*) = {
    append("events", values.map {
      case (k, v) => k -> Code(v)
    })
  }

  def floor(value: Int) = {
    append("floor", value)
  }

  def gridLineColor(value: String) = {
    append("gridLineColor", value)
  }

  def gridLineDashStyle(value: String) = {
    expect(value, "dash", "dot", "solid")
    append("gridLineDashStyle", value)
  }

  def gridLineWidth(value: Int) = {
    append("gridLineWidth", value)
  }

  def gridZIndex(value: Int) = {
    append("gridZIndex", value)
  }

  def id(value: String) = {
    append("id", value)
  }

  def labels(values: (String, Any)*) = {
    append("labels", values.map{
      case ("formatter", v: String) =>
        "formatter" -> placeholdCode(v)
      case ("formatter", v: Code) =>
        "formatter" -> placeholdCode(v)
      case (k, v) => k -> v
    }.toMap)
  }

  def lineColor(value: String) = {
    append("lineColor", value)
  }

  def lineWidth(value: Int) = {
    append("lineWidth", value)
  }

  def linkedTo(value: Int) = {
    append("linkedTo", value)
  }

  def max(value: Double) = {
    append("max", value)
  }

  def maxPadding(value: Double) = {
    append("maxPadding", value)
  }

  def maxZoom(value: Double) = {
    throw new Exception("using minRange instead, deprecated since Highcharts 2.2")
  }

  def min(value: Double) = {
    append("min", value)
  }

  def minPadding(value: Double) = {
    append("minPadding", value)
  }

  def minRange(value: Double) = {
    append("minRange", value)
  }

  def minTickInterval(value: Double) = {
    append("minTickInterval", value)
  }

  def minorGridLineColor(value: String) = {
    append("minorGridLineColor", value)
  }

  def minorGridLineDashStyle(value: String) = {
    append("minorGridLineDashStyle", value)
  }

  def minorGridLineWidth(value: Int) = {
    append("minorGridLineWidth", value)
  }

  def minorTickColor(value: String) = {
    append("minorTickColor", value)
  }

  def minorTickInterval(value: Double) = {
    append("minorTickInterval", value)
  }

  def minorTickLength(value: Int) = {
    append("minorTickLength", value)
  }

  def minorTickPosition(value: String) = {
    append("minorTickPosition", value)
  }

  def minorTickWidth(value: String) = {
    append("minorTickWidth", value)
  }

  def offset(value: Int) = {
    append("offset", value)
  }

  def opposite(value: Boolean) = {
    append("opposite", value)
  }

  def plotBands(values: Map[String, Any]*) = {
    append("plotBands", values.toList)
  }

  // plot ONE band
  def plotBand(values: (String, Any)*) = {
    append("plotBands", List(values.toMap))
  }

  def plotLines(values: Map[String, Any]*) = {
    append("plotLines", values.toList)
  }

  // plot ONE line
  def plotLine(values: (String, Any)*) = {
    append("plotLines", List(values.toMap))
  }

  def reversed(value: Boolean) = {
    append("reversed", value)
  }

  def showEmpty(value: Boolean) = {
    append("showEmpty", value)
  }

  def showFirstLabel(value: Boolean) = {
    append("showFirstLabel", value)
  }

  def showLastLabel(value: String) = {
    append("showLastLabel", value)
  }

  def startOfWeek(value: Int) = {
    append("startOfWeek", value)
  }

  def startOnTick(value: Boolean) = {
    append("startOnTick", value)
  }

  def tickAmount(value: Int) = {
    append("tickAmount", value)
  }

  def tickColor(value: String) = {
    append("tickColor", value)
  }

  def tickInterval(value: Double) = {
    append("tickInterval", value)
  }

  def tickLength(value: Int) = {
    append("tickLength", value)
  }

  def tickPixelInterval(value: Int) = {
    append("tickPixelInterval", value)
  }

  def tickPosition(value: String) = {
    expect(value, "inside", "outside")
    append("tickPosition", value)
  }

  def tickPositioner(code: String) = {
    append("tickPositioner", placeholdCode(code))
  }

  def tickPositions(values: Int*) = {
    append("tickPositions", values.toList)
  }

  def tickWidth(value: Int) = {
    append("tickWidth", value)
  }

  def tickmarkPlacement(value: String) = {
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
  def units(values: List[Any]*) = {
    append("units", values.toList)
  }

  def visible(value: Boolean) = {
    append("units", value)
  }
}

class XAxis(text: String = "") extends Axis(text) {
  override def fieldName: String = "xAxis"
}

class YAxis(text: String = "") extends Axis(text) {
  override def fieldName: String = "yAxis"

  def gridLineInterpolation(value: String) = {
    append("gridLineInterpolation", value)
  }

  def reversedStacks(value: Boolean) = {
    append("reversedStacks", value)
  }

  def stackLabels(values: (String, Any)*) = {
    append("stackLabels", values.map{
      case ("formatter", v: String) => "formatter" -> Code(v)
      case (k, v) => k -> v
    }.toMap)
  }

  def stops(values: List[Any]*) = {
    append("stops", values.toList)
  }
}

object XAxis {
  def apply(text: String): XAxis = new XAxis(text)
}

object YAxis {
  def apply(text: String): YAxis = new YAxis(text)
}



