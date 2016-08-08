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

  def align(value: String) = {
    append("align", value)
  }

  def backgroundColo(value: String) = {
    append("backgroundColo", value)
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

  def enabled(value: Boolean) = {
    append("enabled", value)
  }

  def floating(value: Boolean) = {
    append("floating", value)
  }

  def itemDistance(value: Int) = {
    append("itemDistance", value)
  }

  def itemHiddenStyle(values: (String, Any)*) = {
    append("itemHiddenStyle", values.toMap)
  }

  def itemHoverStyle(values: (String, Any)*) = {
    append("itemHoverStyle", values.toMap)
  }

  def itemMarginBottom(value: Int) = {
    append("itemMarginBottom", value)
  }

  def itemMarginTop(value: Int) = {
    append("itemMarginTop", value)
  }

  def itemStyle(values: (String, Any)*) = {
    append("itemStyle", values.toMap)
  }

  def itemWidth(value: Int) = {
    append("itemWidth", value)
  }

  def labelFormat(value: String) = {
    append("labelFormat", value)
  }

  def labelFormatter(code: String) = {
    append("labelFormatter", placeholdCode(code))
  }

  def layout(value: String) = {
    append("layout", value)
  }

  def lineHeight(value: Int) = {
    append("lineHeight", value)
  }

  def margin(value: Int) = {
    append("margin", value)
  }

  def maxHeight(value: Int) = {
    append("maxHeight", value)
  }

  def navigation(values: (String, Any)*) = {
    append("navigation", values.toMap)
  }

  def padding(value: Int) = {
    append("padding", value)
  }

  def reversed(value: Boolean) = {
    append("reversed", value)
  }

  def rtl(value: Boolean) = {
    append("rtl", value)
  }

  def shadow(value: Boolean) = {
    append("shadow", value)
  }

  def shadow(values: (String, Any)*) = {
    append("shadow", values.toMap)
  }

  def style(values: (String, Any)*) = {
    append("style", values.toMap)
  }

  def symbolHeight(value: Int) = {
    append("symbolHeight", value)
  }

  def symbolPadding(value: Int) = {
    append("symbolPadding", value)
  }

  def symbolRadius(value: String) = {
    append("symbolRadius", value)
  }

  def symbolWidth(value: String) = {
    append("symbolWidth", value)
  }

  def title(values: (String, Any)*) = {
    append("title", values.toMap)
  }

  def useHTML(value: Boolean) = {
    append("useHTML", value)
  }

  def verticalAlign(value: String) = {
    append("verticalAlign", value)
  }

  def width(value: Int) = {
    append("width", value)
  }

  def x(value: Int) = {
    append("x", value)
  }

  def y(value: Int) = {
    append("y", value)
  }
}
