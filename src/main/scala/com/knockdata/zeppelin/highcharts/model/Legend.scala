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

class Legend extends BaseModel with PublicApply {
  override def fieldName: String = "legend"

  def align(value: String): this.type = {
    append("align", value)
  }

  def backgroundColo(value: String): this.type = {
    append("backgroundColo", value)
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

  def enabled(value: Boolean): this.type = {
    append("enabled", value)
  }

  def floating(value: Boolean): this.type = {
    append("floating", value)
  }

  def itemDistance(value: Int): this.type = {
    append("itemDistance", value)
  }

  def itemHiddenStyle(values: (String, Any)*): this.type = {
    append("itemHiddenStyle", values.toMap)
  }

  def itemHoverStyle(values: (String, Any)*): this.type = {
    append("itemHoverStyle", values.toMap)
  }

  def itemMarginBottom(value: Int): this.type = {
    append("itemMarginBottom", value)
  }

  def itemMarginTop(value: Int): this.type = {
    append("itemMarginTop", value)
  }

  def itemStyle(values: (String, Any)*): this.type = {
    append("itemStyle", values.toMap)
  }

  def itemWidth(value: Int): this.type = {
    append("itemWidth", value)
  }

  def labelFormat(value: String): this.type = {
    append("labelFormat", value)
  }

  def labelFormatter(code: String): this.type = {
    append("labelFormatter", placeholdCode(code))
  }

  def layout(value: String): this.type = {
    append("layout", value)
  }

  def lineHeight(value: Int): this.type = {
    append("lineHeight", value)
  }

  def margin(value: Int): this.type = {
    append("margin", value)
  }

  def maxHeight(value: Int): this.type = {
    append("maxHeight", value)
  }

  def navigation(values: (String, Any)*): this.type = {
    append("navigation", values.toMap)
  }

  def padding(value: Int): this.type = {
    append("padding", value)
  }

  def reversed(value: Boolean): this.type = {
    append("reversed", value)
  }

  def rtl(value: Boolean): this.type = {
    append("rtl", value)
  }

  def shadow(value: Boolean): this.type = {
    append("shadow", value)
  }

  def shadow(values: (String, Any)*): this.type = {
    append("shadow", values.toMap)
  }

  def style(values: (String, Any)*): this.type = {
    append("style", values.toMap)
  }

  def symbolHeight(value: Int): this.type = {
    append("symbolHeight", value)
  }

  def symbolPadding(value: Int): this.type = {
    append("symbolPadding", value)
  }

  def symbolRadius(value: String): this.type = {
    append("symbolRadius", value)
  }

  def symbolWidth(value: String): this.type = {
    append("symbolWidth", value)
  }

  def title(values: (String, Any)*): this.type = {
    append("title", values.toMap)
  }

  def useHTML(value: Boolean): this.type = {
    append("useHTML", value)
  }

  def verticalAlign(value: String): this.type = {
    append("verticalAlign", value)
  }

  def width(value: Int): this.type = {
    append("width", value)
  }

  def x(value: Int): this.type = {
    append("x", value)
  }

  def y(value: Int): this.type = {
    append("y", value)
  }
}
