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

package com.knockdata.spark.highcharts.model

import com.knockdata.spark.highcharts.base.BaseModel

/**
  * ref:
  *     http://www.highcharts.com/docs/maps/color-axis
  *     http://api.highcharts.com/highmaps/colorAxis
  */
class ColorAxis extends BaseModel with PublicApply {
  override def fieldName: String = "colorAxis"

  def className(value: String): this.type = {
    append("className", value)
  }

  def typ(value: String): this.type = {
    append("type", value)
  }

  def dataClassColor(value: String): this.type = {
    append("dataClassColor", value)
  }

  // TODO
//  def dataClasses(value: String): this.type = {
//    append("dataClasses", value)
//  }

  def description(value: String): this.type = {
    append("categories", value)
  }

  def endOnTick(value: Boolean): this.type = {
    append("endOnTick", value)
  }

  def events(values: (String, String)*): this.type = {
    append("events", values.map {
      case (k, v) => k -> Code(v)
    })
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

  // TODO
  // marker

  def max(value: Double): this.type = {
    append("max", value)
  }

  def maxColor(value: String): this.type = {
    append("maxColor", value)
  }

  def maxPadding(value: Double): this.type = {
    append("maxPadding", value)
  }


  def min(value: Double): this.type = {
    append("min", value)
  }

  def minColor(value: String): this.type = {
    append("minColor", value)
  }

  def minPadding(value: Double): this.type = {
    append("minPadding", value)
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

  def reversed(value: Boolean): this.type = {
    append("reversed", value)
  }

  def showFirstLabel(value: Boolean): this.type = {
    append("showFirstLabel", value)
  }

  def showInLegend(value: Boolean): this.type = {
    append("showInLegend", value)
  }

  def showLastLabel(value: String): this.type = {
    append("showLastLabel", value)
  }

  def softMin(value: Double): this.type = {
    append("softMin", value)
  }

  /**
    *
    * @param values: The first value is from 0 - 1 to for the value range
    *              The second value is a color
    *              e.g.
    *              List(0, color1), List(0.3, color2), List(0.7, color2), List(1, color3)
    * @return
    */
  def stops(values: List[Any]*): this.type = {
    append("stops", values)
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
}


object ColorAxis {
  def colorRange(minColor: String, maxColor: String): ColorAxis = {
    new ColorAxis().minColor(minColor).maxColor(maxColor)
  }

  def range(min: Double, max: Double, minColor: String, maxColor: String): ColorAxis = {
    new ColorAxis().min(min).max(max).minColor(minColor).maxColor(maxColor)
  }

  def stops(values: List[Any]*): ColorAxis = {
    new ColorAxis().stops(values:_*)
  }

}








