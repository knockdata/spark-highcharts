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

private[highcharts] class Pyramid extends BasePlotOptions with PublicApply {
  def fieldName = "pyramid"

  def allowPointSelect(value: Boolean): this.type = {
    append("allowPointSelect", value)
  }

  def borderColor(value: String): this.type = {
    append("borderColor", value)
  }

  def borderWidth(value: Int): this.type = {
    append("borderWidth", value)
  }

  def center(values: Any*): this.type = {
    append("center", values.toList)
  }

  def colors(values: String*): this.type = {
    append("colors", values.toList)
  }

  def dataLabels(values: (String, Any)*): this.type = {
    append("dataLabels", values.toMap)
  }

  def depth(value: Int): this.type = {
    append("depth", value)
  }

  def height(value: Int): this.type = {
    append("height", value)
  }

  def linkedTo(value: String): this.type = {
    append("linkedTo", value)
  }

  def minSize(value: Int): this.type = {
    append("minSize", value)
  }

  def reversed(value: Boolean): this.type = {
    append("reversed", value)
  }

  def shadow(value: Boolean): this.type = {
    append("shadow", value)
  }

  def showInLegend(value: Boolean): this.type = {
    append("showInLegend", value)
  }

  def slicedOffset(value: Int): this.type = {
    append("slicedOffset", value)
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

  def width(value: Int): this.type = {
    append("width", value)
  }
}
