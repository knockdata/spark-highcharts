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

class Subtitle(text: String = "") extends BaseModel with PublicApply {
  override def fieldName: String = "subtitle"

  append("text", text)

  //  align: String = "center", floating: Boolean = false, margin: Int = 15
  def align(align: String) = {
    append("align", align)
  }

  def floating(value: String) = {
    append("floating", value)
  }

  def style(values: (String, Any)*) = {
    append("style", values.toMap)
  }

  def useHTML(value: Boolean) = {
    append("useHTML", value)
  }

  def verticalAlign(value: String) = {
    append("verticalAlign", value)
  }

  def x(value: Int) = {
    append("x", value)
  }

  def y(value: Int) = {
    append("y", value)
  }
}

object Subtitle {
  def apply(text: String): Subtitle = new Subtitle(text)
}
