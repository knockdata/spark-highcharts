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

private[zeppelin] class Gauge extends BasePlotOptions with PublicApply {
  def fieldName = "gauge"

  def animation(value: Boolean): this.type = {
    append("animation", value)
  }

  def color(value: String): this.type = {
    append("color", value)
  }

  def dataLabels(values: (String, Any)*): this.type = {
    append("dataLabels", values.toMap)
  }

  def dial(values: (String, Any)*): this.type = {
    append("dial", values.toMap)
  }

  def linkedTo(value: String): this.type = {
    append("linkedTo", value)
  }

  def negativeColor(value: String): this.type = {
    append("negativeColor", value)
  }

  def overshoot(value: Int): this.type = {
    append("overshoot", value)
  }

  def pivot(values: (String, Any)*): this.type = {
    append("pivot", values.toMap)
  }

  def showCheckbox(value: Boolean): this.type = {
    append("showCheckbox", value)
  }

  def showInLegend(value: Boolean): this.type = {
    append("showInLegend", value)
  }

  def threshold(value: Int): this.type = {
    append("threshold", value)
  }

  def wrap(value: Boolean): this.type = {
    append("wrap", value)
  }
}
