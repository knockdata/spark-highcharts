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
import com.knockdata.zeppelin.highcharts.convert
import org.apache.spark.sql.DataFrame

class Series(val values: List[Any]) extends BaseModel with PublicApply {
  override def fieldName: String = "series"

  var vs = values

  def id(value: String): this.type = {
    append("id", value)
  }

  def name(value: String): this.type = {
    append("name", value)
  }

  def typ(typ: String): this.type = {
    append("type", typ)
  }

  def center(x: Int, y: Int): this.type = {
    append("center", Seq(x, y))
  }

  def size(s: Int): this.type = {
    append("size", s)
  }

  def size(s: String): this.type = {
    append("size", s)
  }

  def innerSize(value: String): this.type = {
    append("innerSize", value)
  }

  def showInLegend(show: Boolean): this.type = {
    append("showInLegend", show)
  }

  def dataLabels(values: (String, Any)*): this.type = {
    append("dataLabels",
      values.map {
        case ("formatter", v: String) =>
          "formatter" -> placeholdCode(v)
        case (k, v) =>
          k -> v
      }.toMap)
  }

  override def preProcessResult(): Unit = {
    append("data", vs)
    super.preProcessResult()
  }
}

object Series {

  def apply(dataFrame: DataFrame, colDefs: (String, Any)*): Series = {
    convert(dataFrame, colDefs.toList)
  }

  def apply(values: Any*) = new Series(values.toList)
}



