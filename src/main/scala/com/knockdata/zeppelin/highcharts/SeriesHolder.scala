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

  private val colDefsBuffer = mutable.Buffer[(String, Any)]()
  private val drillsDefsBuffer = mutable.Buffer[mutable.Buffer[(String, Any)]]()

  private var currentDefs = colDefsBuffer

  def seriesCol(columnName: String) = {
    _seriesCol = Some(columnName)
    this
  }

  def series(defs: (String, Any)*) = {
    currentDefs ++= defs
    this
  }

  def options(seriesOption: BasePlotOptions) = {
    currentDefs += "options" -> seriesOption
    this
  }

  def orderBy(column: Column) = {
    currentDefs += "orderBy" -> column
    this
  }

  def drilldown(defs: (String, Any)*) = {
    currentDefs = defs.toBuffer

    drillsDefsBuffer += currentDefs
    this
  }



  def result: (List[Series], List[Series]) = {
    val colDefs = colDefsBuffer.toList
    val drillsDefList = drillsDefsBuffer.toList.map(_.toList)

    _seriesCol match {
      case None =>
        convert(dataFrame, colDefs, drillsDefList)
      case Some(seriesCol) =>
        convert(dataFrame, seriesCol, colDefs, drillsDefList)
    }
  }
}
