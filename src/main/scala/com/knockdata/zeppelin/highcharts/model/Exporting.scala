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

class Exporting() extends BaseModel with PublicApply {
  override def fieldName: String = "exporting"

  def enabled(value: Boolean): this.type = {
    append("enabled", value)
  }

  def filename(value: String): this.type = {
    append("filename", value)
  }

  def printMaxWidth(value: Int): this.type = {
    append("printMaxWidth", value)
  }

  def scale(value: Int): this.type = {
    append("scale", value)
  }

  def sourceHeight(value: Int): this.type = {
    append("sourceHeight", value)
  }

  def sourceWidth(value: Int): this.type = {
    append("sourceWidth", value)
  }

  def typ(value: String): this.type = {
    append("type", value)
  }

  def width(value: Int): this.type = {
    append("width", value)
  }
}
