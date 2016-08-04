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

private[zeppelin] class Polygon extends BasePlotOptions with PublicApply {
  def fieldName = "polygon"

  def allowPointSelect(value: Boolean): this.type = {
    append("allowPointSelect", value)
  }

  def animation(value: Boolean): this.type = {
    append("animation", value)
  }

  def color(value: String): this.type = {
    append("color", value)
  }

  def cropThreshold(value: Int): this.type = {
    append("cropThreshold", value)
  }

  def dashStyle(value: String): this.type = {
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

  def dataLabels(values: (String, Any)*): this.type = {
    append("dataLabels", values.toMap)
  }

  def lineWidth(value: Int): this.type = {
    append("lineWidth", value)
  }

  def linkedTo(value: String): this.type = {
    append("linkedTo", value)
  }

  def marker(values: (String, Any)*): this.type = {
    append("marker", values.toMap)
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

  def pointStart(value: Int): this.type = {
    append("pointStart", value)
  }

  def shadow(value: Boolean): this.type = {
    append("shadow", value)
  }

  def showCheckbox(value: Boolean): this.type = {
    append("showCheckbox", value)
  }

  def showInLegend(value: Boolean): this.type = {
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

  def turboThreshold(value: Int): this.type = {
    append("turboThreshold", value)
  }
}
