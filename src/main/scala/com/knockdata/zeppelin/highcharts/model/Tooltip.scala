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

  def animation(value: Boolean): this.type = {
    append("animation", value)
  }

  def backgroundColor(value: String): this.type = {
    append("backgroundColor", value)
  }

  def borderColor(value: String): this.type = {
    append("borderColor", value)
  }

  def borderRadius(value: Int): this.type = {
    append("borderRadius", value)
  }

  def crosshairs(value: String): this.type = {
    throw new Exception("using crosshairs in Axis instead")
  }

  def dateTimeLabelFormats(value: String): this.type = {
    append("dateTimeLabelFormats", value)
  }

  def enabled(value: Boolean): this.type = {
    append("enabled", value)
  }

  def followPointer(value: Boolean): this.type = {
    append("followPointer", value)
  }

  def followTouchMove(value: String): this.type = {
    append("followTouchMove", value)
  }

  def footerFormat(value: String): this.type = {
    append("footerFormat", value)
  }

  def formatter(value: String): this.type = {
    append("formatter", placeholdCode(value))
  }

  def headerFormat(format: String): this.type = {
    append("headerFormat", format)
  }

  def hideDelay(value: Int): this.type = {
    append("hideDelay", value)
  }

  def pointFormat(format: String): this.type = {
    append("pointFormat", format)
  }

  def pointFormatter(value: String): this.type = {
    append("pointFormatter", placeholdCode(value))
  }

  def positioner(value: String): this.type = {
    append("positioner", placeholdCode(value))
  }

  def shadow(value: Boolean): this.type = {
    append("shadow", value)
  }

  def shape(value: String): this.type = {
    append("shape", value)
  }

  def shared(value: Boolean): this.type = {
    append("shared", value)
  }

  def snap(value: Int): this.type = {
    append("snap", value)
  }

  def style(values: (String, Any)*): this.type = {
    append("style", values.toMap)
  }

  def useHTML(value: Boolean): this.type = {
    append("useHTML", value)
  }

  def valueDecimals(value: Int): this.type = {
    append("valueDecimals", value)
  }

  def valuePrefix(value: String): this.type = {
    append("valuePrefix", value)
  }

  def valueSuffix(value: String): this.type = {
    append("valueSuffix", value)
  }

  def xDateFormat(value: String): this.type = {
    append("xDateFormat", value)
  }
}
