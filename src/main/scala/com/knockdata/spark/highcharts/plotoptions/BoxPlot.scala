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

package com.knockdata.spark.highcharts.plotoptions

import com.knockdata.spark.highcharts.model._
import com.knockdata.spark.highcharts.base._

private[highcharts] class BoxPlot extends BasePlotOptions with PublicApply {
  def fieldName = "boxplot"

  def allowPointSelect(value: Boolean): this.type = {
    append("allowPointSelect", value)
  }

  def color(value: String): this.type = {
    append("color", value)
  }

  def colorByPoint(value: Boolean): this.type = {
    append("colorByPoint", value)
  }

  def colors(values: String*): this.type = {
    append("colors", values.toList)
  }

  def depth(value: Int): this.type = {
    append("depth", value)
  }

  def edgeColor(value: String): this.type = {
    append("edgeColor", value)
  }

  def edgeWidth(value: Int): this.type = {
    append("edgeWidth", value)
  }

  def fillColor(value: String): this.type = {
    append("fillColor", value)
  }

  def groupPadding(value: Double): this.type = {
    append("groupPadding", value)
  }

  def groupZPadding(value: Int): this.type = {
    append("groupZPadding", value)
  }

  def grouping(value: Boolean): this.type = {
    append("grouping", value)
  }

  def lineWidth(value: Int): this.type = {
    append("lineWidth", value)
  }

  def linkedTo(value: String): this.type = {
    append("linkedTo", value)
  }

  def maxPointWidth(value: Int): this.type = {
    append("maxPointWidth", value)
  }

  def medianColor(value: String): this.type = {
    append("medianColor", value)
  }

  def medianWidth(value: Int): this.type = {
    append("medianWidth", value)
  }

  def negativeColor(value: String): this.type = {
    append("negativeColor", value)
  }

  def pointInterval(value: Int): this.type = {
    append("pointInterval", value)
  }

  def pointIntervalUnit(value: String): this.type = {
    append("pointIntervalUnit", value)
  }

  def pointPadding(value: Double): this.type = {
    append("pointPadding", value)
  }

  def pointPlacement(value: String): this.type = {
    // null, "on", "between"
    append("pointPlacement", value)
  }

  def pointRange(value: Int): this.type = {
    append("pointRange", value)
  }

  def pointStart(value: Int): this.type = {
    append("pointStart", value)
  }

  def pointWidth(value: Int): this.type = {
    append("pointWidth", value)
  }

  def showCheckbox(value: Int): this.type = {
    append("showCheckbox", value)
  }

  def showInLegend(value: Int): this.type = {
    append("showInLegend", value)
  }

  // the value only have one item which is hover
  def states(values: (String, Map[String, Any])*): this.type = {
    val hover = values.collect {
      case ("hover", v) => v
    }.head

    append("states", "hover", hover)
  }

  /**
    * it is states.hover, since only hover in states
    * so just using one function without embedded structure
    */
  def statesHover(values: (String, Any)*): this.type = {
    append("states", "hover", values.toMap)
  }

  def stemColor(value: String): this.type = {
    append("stemColor", value)
  }

  def stemDashStyle(value: String): this.type = {
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

  def stemWidth(value: Int): this.type = {
    append("stemWidth", value)
  }

  def turboThreshold(value: Int): this.type = {
    append("turboThreshold", value)
  }

  def whiskerColor(value: String): this.type = {
    append("whiskerColor", value)
  }

  def whiskerLength(value: Int): this.type = {
    append("whiskerLength", value)
  }

  def whiskerWidth(value: Int): this.type = {
    append("whiskerWidth", value)
  }
}
