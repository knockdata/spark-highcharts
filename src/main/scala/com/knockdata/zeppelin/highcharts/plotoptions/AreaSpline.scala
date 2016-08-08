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

package com.knockdata.zeppelin.highcharts.plotoptions

import com.knockdata.zeppelin.highcharts.model._
import com.knockdata.zeppelin.highcharts.base._


private[highcharts] class AreaSpline extends BasePlotOptions with PublicApply {

  def fieldName = "areaspline"

  def allowPointSelect(value: Boolean) = {
    append("allowPointSelect", value)
  }

  // NOT in API DOC
  def ange(value: Any) = {
    append("ange", value)
  }

  def animation(value: Boolean) = {
    append("animation", value)
  }

  def color(value: String) = {
    append("color", value)
  }

  def connectEnds(value: Boolean) = {
    append("connectEnds", value)
  }

  def connectNulls(value: Boolean) = {
    append("connectNulls", value)
  }

  def cropThreshold(value: Int) = {
    append("cropThreshold", value)
  }

  def dashStyle(value: String) = {
    expect(value, "Solid",
      "ShortDash",
      "ShortDot",
      "ShortDashDot",
      "ShortDashDotDot",
      "Dot",
      "Dash",
      "LongDash",
      "DashDot",
      "LongDashDot",
      "LongDashDotDot")
    append("dashStyle", value)
  }

  def dataLabels(values: (String, Any)*) = {
    append("dataLabels", values.toMap)
  }

  def fillColor(value: String) = {
    append("fillColor", value)
  }

  def fillOpacity(value: Double) = {
    append("fillOpacity", value)
  }

  def lineColor(value: String) = {
    append("lineColor", value)
  }

  def lineWidth(value: Int) = {
    append("lineWidth", value)
  }

  def linecap(value: String) = {
    // round
    append("linecap", value)
  }

  def linkedTo(value: String) = {
    append("linkedTo", value)
  }

  def marker(values: (String, Any)*) = {
    append("marker", values.toMap)
  }

  def negativeColor(value: String) = {
    append("negativeColor", value)
  }

  def negativeFillColor(value: String) = {
    append("negativeFillColor", value)
  }

  def pointInterval(value: Int) = {
    append("pointInterval", value)
  }

  def pointIntervalUnit(value: String) = {
    append("pointIntervalUnit", value)
  }

  def pointPlacement(value: String) = {
    // null, "on", "between"
    append("pointPlacement", value)
  }

  def pointPlacement(value: Double) = {
    append("pointPlacement", value)
  }

  def pointStart(value: Int) = {
    append("pointStart", value)
  }

  def shadow(value: String) = {
    append("shadow", value)
  }

  def showCheckbox(value: Boolean) = {
    append("showCheckbox", value)
  }

  def showInLegend(value: Boolean) = {
    append("showInLegend", value)
  }

  def softThreshold(value: Boolean) = {
    append("softThreshold", value)
  }

  def stacking(value: String) = {
    // "normal" to stack by value or "percent".
    append("stacking", value)
  }

  // the value only have one item which is hover
  def states(values: (String, Map[String, Any])*) = {
    val hover = values.collect {
      case ("hover", v) => v
    }.head

    append("states", "hover", hover)
  }

  /**
    * it is states.hover, since only hover in states
    * so just using one function without embedded structure
    */
  def statesHover(values: (String, Any)*) = {
    append("states", "hover", values.toMap)
  }

  def threshold(value: Int) = {
    append("threshold", value)
  }

  def trackByArea(value: Boolean) = {
    append("trackByArea", value)
  }

  def turboThreshold(value: Int) = {
    append("turboThreshold", value)
  }
}
