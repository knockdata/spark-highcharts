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

private[highcharts] class BoxPlot extends BasePlotOptions with PublicApply {
  def fieldName = "boxplot"

  def allowPointSelect(value: Boolean) = {
    append("allowPointSelect", value)
  }

  def color(value: String) = {
    append("color", value)
  }

  def colorByPoint(value: Boolean) = {
    append("colorByPoint", value)
  }

  def colors(values: String*) = {
    append("colors", values.toList)
  }

  def depth(value: Int) = {
    append("depth", value)
  }

  def edgeColor(value: String) = {
    append("edgeColor", value)
  }

  def edgeWidth(value: Int) = {
    append("edgeWidth", value)
  }

  def fillColor(value: String) = {
    append("fillColor", value)
  }

  def groupPadding(value: Double) = {
    append("groupPadding", value)
  }

  def groupZPadding(value: Int) = {
    append("groupZPadding", value)
  }

  def grouping(value: Boolean) = {
    append("grouping", value)
  }

  def lineWidth(value: Int) = {
    append("lineWidth", value)
  }

  def linkedTo(value: String) = {
    append("linkedTo", value)
  }

  def maxPointWidth(value: Int) = {
    append("maxPointWidth", value)
  }

  def medianColor(value: String) = {
    append("medianColor", value)
  }

  def medianWidth(value: Int) = {
    append("medianWidth", value)
  }

  def negativeColor(value: String) = {
    append("negativeColor", value)
  }

  def pointInterval(value: Int) = {
    append("pointInterval", value)
  }

  def pointIntervalUnit(value: String) = {
    append("pointIntervalUnit", value)
  }

  def pointPadding(value: Double) = {
    append("pointPadding", value)
  }

  def pointPlacement(value: String) = {
    // null, "on", "between"
    append("pointPlacement", value)
  }

  def pointRange(value: Int) = {
    append("pointRange", value)
  }

  def pointStart(value: Int) = {
    append("pointStart", value)
  }

  def pointWidth(value: Int) = {
    append("pointWidth", value)
  }

  def showCheckbox(value: Int) = {
    append("showCheckbox", value)
  }

  def showInLegend(value: Int) = {
    append("showInLegend", value)
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

  def stemColor(value: String) = {
    append("stemColor", value)
  }

  def stemDashStyle(value: String) = {
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
    append("stemDashStyle", value)
  }

  def stemWidth(value: Int) = {
    append("stemWidth", value)
  }

  def turboThreshold(value: Int) = {
    append("turboThreshold", value)
  }

  def whiskerColor(value: String) = {
    append("whiskerColor", value)
  }

  def whiskerLength(value: Int) = {
    append("whiskerLength", value)
  }

  def whiskerWidth(value: Int) = {
    append("whiskerWidth", value)
  }
}
