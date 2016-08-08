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

private[highcharts] class Funnel extends BasePlotOptions with PublicApply {
  def fieldName = "funnel"

  def allowPointSelect(value: Boolean) = {
    append("allowPointSelect", value)
  }

  def borderColor(value: String) = {
    append("borderColor", value)
  }

  def borderWidth(value: Int) = {
    append("borderWidth", value)
  }

  def center(value: Any*) = {
    append("center", value.toList)
  }

  def colors(value: String*) = {
    append("colors", value)
  }

  def dataLabels(values: (String, Any)*) = {
    append("dataLabels", values.toMap)
  }

  def depth(value: Int) = {
    append("depth", value)
  }

  def height(value: Int) = {
    append("height", value)
  }

  def linkedTo(value: String) = {
    append("linkedTo", value)
  }

  def minSize(value: Int) = {
    append("minSize", value)
  }

  def neckHeight(value: Int) = {
    append("neckHeight", value)
  }

  def neckWidth(value: Int) = {
    append("neckWidth", value)
  }

  def reversed(value: Boolean) = {
    append("reversed", value)
  }

  def shadow(value: Boolean) = {
    append("shadow", value)
  }

  def showInLegend(value: Boolean) = {
    append("showInLegend", value)
  }

  def slicedOffset(value: Int) = {
    append("slicedOffset", value)
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

  def width(value: Int) = {
    append("width", value)
  }
}
