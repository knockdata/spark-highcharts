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

package com.knockdata.zeppelin.highcharts

import com.knockdata.zeppelin.highcharts.base._
import com.knockdata.zeppelin.highcharts.model._
import org.apache.spark.sql.{Column, DataFrame}

import scala.collection.mutable

private[highcharts] class SeriesHolder(dataFrame: DataFrame) {
  private var _seriesCol: Option[String] = None

  private var defsBuffer = mutable.Buffer[(String, Any)]()
  private val allDefsBuffer = mutable.Buffer[List[(String, Any)]]()

  def seriesCol(columnName: String) = {
    _seriesCol = Some(columnName)
    this
  }

  def series(defs: (String, Any)*) = {
    defsBuffer ++= defs
    this
  }

  def options(seriesOption: BasePlotOptions) = {
    defsBuffer += "options" -> seriesOption
    this
  }

  def orderBy(column: Column) = {
    defsBuffer += "orderBy" -> column
    this
  }

  // always using without replacement, can not specify seed
  // https://www.ma.utexas.edu/users/parker/sampling/repl.htm
  def sample(fractions: Double) = {
    defsBuffer += "sample" -> fractions
    this
  }

  def take(n: Int) = {
    defsBuffer += "take" -> n
    this
  }

  def drilldown(defs: (String, Any)*) = {
    allDefsBuffer += defsBuffer.toList

    defsBuffer = mutable.Buffer[(String, Any)](defs:_*)

    this
  }

  def result: (List[Series], List[Series]) = {
    if (defsBuffer.nonEmpty) {
      allDefsBuffer += defsBuffer.toList
    }

    _seriesCol match {
      case None =>
        convert(dataFrame, allDefsBuffer.toList)
      case Some(seriesCol) =>
        convert(dataFrame, seriesCol, allDefsBuffer.toList)
    }
  }
}
