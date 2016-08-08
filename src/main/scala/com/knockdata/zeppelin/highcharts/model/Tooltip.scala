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

package com.knockdata.zeppelin.highcharts.model

import com.knockdata.zeppelin.highcharts.base.BaseModel

class Tooltip extends BaseModel with PublicApply {
  override def fieldName: String = "tooltip"

  def animation(value: Boolean) = {
    append("animation", value)
  }

  def backgroundColor(value: String) = {
    append("backgroundColor", value)
  }

  def borderColor(value: String) = {
    append("borderColor", value)
  }

  def borderRadius(value: Int) = {
    append("borderRadius", value)
  }

  def crosshairs(value: String) = {
    throw new Exception("using crosshairs in Axis instead")
  }

  def dateTimeLabelFormats(value: String) = {
    append("dateTimeLabelFormats", value)
  }

  def enabled(value: Boolean) = {
    append("enabled", value)
  }

  def followPointer(value: Boolean) = {
    append("followPointer", value)
  }

  def followTouchMove(value: String) = {
    append("followTouchMove", value)
  }

  def footerFormat(value: String) = {
    append("footerFormat", value)
  }

  def formatter(value: String) = {
    append("formatter", placeholdCode(value))
  }

  def headerFormat(format: String) = {
    append("headerFormat", format)
  }

  def hideDelay(value: Int) = {
    append("hideDelay", value)
  }

  def pointFormat(format: String) = {
    append("pointFormat", format)
  }

  def pointFormatter(value: String) = {
    append("pointFormatter", placeholdCode(value))
  }

  def positioner(value: String) = {
    append("positioner", placeholdCode(value))
  }

  def shadow(value: Boolean) = {
    append("shadow", value)
  }

  def shape(value: String) = {
    append("shape", value)
  }

  def shared(value: Boolean) = {
    append("shared", value)
  }

  def snap(value: Int) = {
    append("snap", value)
  }

  def style(values: (String, Any)*) = {
    append("style", values.toMap)
  }

  def useHTML(value: Boolean) = {
    append("useHTML", value)
  }

  def valueDecimals(value: Int) = {
    append("valueDecimals", value)
  }

  def valuePrefix(value: String) = {
    append("valuePrefix", value)
  }

  def valueSuffix(value: String) = {
    append("valueSuffix", value)
  }

  def xDateFormat(value: String) = {
    append("xDateFormat", value)
  }
}
