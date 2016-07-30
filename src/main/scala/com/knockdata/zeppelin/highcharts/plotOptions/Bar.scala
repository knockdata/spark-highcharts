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

package com.knockdata.zeppelin.highcharts.plotOptions

import com.knockdata.zeppelin.highcharts.model._
import com.knockdata.zeppelin.highcharts.base._

class Bar extends BasePlotOptions with PublicApply {
  def fieldName = "bar"

  def allowPointSelect(value: Boolean): this.type = {
    append("allowPointSelect", value)
  }

  def animation(value: Boolean): this.type = {
    append("animation", value)
  }

  def borderColor(value: String): this.type = {
    append("borderColor", value)
  }

  def borderRadius(value: Int): this.type = {
    append("borderRadius", value)
  }

  def borderWidth(value: Int): this.type = {
    append("borderWidth", value)
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

  def cropThreshold(value: Int): this.type = {
    append("cropThreshold", value)
  }

  def dataLabels(values: (String, Any)*): this.type = {
    append("dataLabels", values.toMap)
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

  def groupPadding(value: Double): this.type = {
    append("groupPadding", value)
  }

  def groupZPadding(value: Double): this.type = {
    append("groupZPadding", value)
  }

  def grouping(value: Boolean): this.type = {
    append("grouping", value)
  }

  def linkedTo(value: String): this.type = {
    append("linkedTo", value)
  }

  def maxPointWidth(value: Int): this.type = {
    append("maxPointWidth", value)
  }

  def minPointLength(value: Int): this.type = {
    append("minPointLength", value)
  }

  def negativeColor(value: String): this.type = {
    append("negativeColor", value)
  }

  def pointInterval(value: Int): this.type = {
    append("pointInterval", value)
  }

  def pointIntervalUnit(value: Int): this.type = {
    append("pointIntervalUnit", value)
  }

  def pointPadding(value: Double): this.type = {
    append("pointPadding", value)
  }


  def pointPlacement(value: Any): this.type = {
    // null, "on", "between"
    append("pointPlacement", value)
  }

  def pointRange(value: Double): this.type = {
    append("pointRange", value)
  }

  def pointStart(value: Int): this.type = {
    append("pointStart", value)
  }

  def pointWidth(value: Int): this.type = {
    append("pointWidth", value)
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

  def softThreshold(value: Boolean): this.type = {
    append("softThreshold", value)
  }


  def stacking(value: String): this.type = {
    // "normal" to stack by value or "percent".
    append("stacking", value)
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

  def threshold(value: Int): this.type = {
    append("threshold", value)
  }

  def turboThreshold(value: Int): this.type = {
    append("turboThreshold", value)
  }
}
