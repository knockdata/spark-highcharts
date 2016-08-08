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

private[highcharts] class Column extends BasePlotOptions with PublicApply {
  def fieldName = "column"

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

  def borderColor(value: String) = {
    append("borderColor", value)
  }

  def borderRadius(value: Int) = {
    append("borderRadius", value)
  }

  def borderWidth(value: Int) = {
    append("borderWidth", value)
  }

  def color(value: String) = {
    append("color", value)
  }

  def colorByPoint(value: Int) = {
    append("colorByPoint", value)
  }

  def colors(value: String*) = {
    append("colors", value)
  }

  def cropThreshold(value: Int) = {
    append("cropThreshold", value)
  }

  def dataLabels(values: (String, Any)*) = {
    append("dataLabels", values.toMap)
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

  def groupPadding(value: Double) = {
    append("groupPadding", value)
  }

  def groupZPadding(value: Double) = {
    append("groupZPadding", value)
  }

  def grouping(value: Boolean) = {
    append("grouping", value)
  }

  def linkedTo(value: String) = {
    append("linkedTo", value)
  }

  def maxPointWidth(value: Int) = {
    append("maxPointWidth", value)
  }

  def minPointLength(value: Int) = {
    append("minPointLength", value)
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

  def pointRange(value: Double) = {
    append("pointRange", value)
  }

  def pointStart(value: Int) = {
    append("pointStart", value)
  }

  def pointWidth(value: Int) = {
    append("pointWidth", value)
  }

  def shadow(value: Boolean) = {
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

  def turboThreshold(value: Int) = {
    append("turboThreshold", value)
  }
}
