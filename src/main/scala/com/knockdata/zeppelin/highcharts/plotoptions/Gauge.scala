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

private[highcharts] class Gauge extends BasePlotOptions with PublicApply {
  def fieldName = "gauge"

  def animation(value: Boolean) = {
    append("animation", value)
  }

  def color(value: String) = {
    append("color", value)
  }

  def dataLabels(values: (String, Any)*) = {
    append("dataLabels", values.toMap)
  }

  def dial(values: (String, Any)*) = {
    append("dial", values.toMap)
  }

  def linkedTo(value: String) = {
    append("linkedTo", value)
  }

  def negativeColor(value: String) = {
    append("negativeColor", value)
  }

  def overshoot(value: Int) = {
    append("overshoot", value)
  }

  def pivot(values: (String, Any)*) = {
    append("pivot", values.toMap)
  }

  def showCheckbox(value: Boolean) = {
    append("showCheckbox", value)
  }

  def showInLegend(value: Boolean) = {
    append("showInLegend", value)
  }

  def threshold(value: Int) = {
    append("threshold", value)
  }

  def wrap(value: Boolean) = {
    append("wrap", value)
  }
}
