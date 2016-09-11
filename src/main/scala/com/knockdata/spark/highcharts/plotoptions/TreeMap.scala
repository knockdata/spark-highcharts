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

private[highcharts] class TreeMap extends BasePlotOptions with PublicApply {
  def fieldName = "treemap"

  def allowDrillToNode(value: Boolean): this.type = {
    append("allowDrillToNode", value)
  }

  def allowPointSelect(value: Boolean): this.type = {
    append("allowPointSelect", value)
  }

  def alternateStartingDirection(value: Boolean): this.type = {
    append("alternateStartingDirection", value)
  }

  def animation(value: Boolean): this.type = {
    append("animation", value)
  }

  def borderColor(value: String): this.type = {
    append("borderColor", value)
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

  def colors(values: Any*): this.type = {
    append("colors", values.toList)
  }

  def cropThreshold(value: Int): this.type = {
    append("cropThreshold", value)
  }

  def dataLabels(values: (String, Any)*): this.type = {
    append("dataLabels", values.toMap)
  }

  def interactByLeaf(value: Boolean): this.type = {
    append("interactByLeaf", value)
  }

  def layoutAlgorithm(value: String): this.type = {
    expect("value", "sliceAndDice", "stripes", "squarified", "strip")
    append("layoutAlgorithm", value)
  }

  def layoutStartingDirection(value: String): this.type = {
    expect(value, "vertical", "horizontal")
    append("layoutStartingDirection", value)
  }

  def levelIsConstant(value: Boolean): this.type = {
    append("levelIsConstant", value)
  }

  def levels(values: Map[String, Any]*): this.type = {
    append("levels", values.toList)
  }

  def linkedTo(value: String): this.type = {
    append("linkedTo", value)
  }

  def maxPointWidth(value: Int): this.type = {
    append("maxPointWidth", value)
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

  def sortIndex(value: Int): this.type = {
    append("sortIndex", value)
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
