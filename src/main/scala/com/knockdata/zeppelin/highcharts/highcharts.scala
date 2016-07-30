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

import base.BaseModel
import org.apache.spark.sql.DataFrame

object highcharts {
  def apply(dataFrame: DataFrame): HighchartsHolder = {
    new HighchartsHolder((dataFrame))
  }

  def apply(dataFrame: DataFrame,
            colDefs: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, colDefs).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            seriesCol: String,
            colDefs: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, seriesCol, colDefs).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            colDefs: List[(String, Any)],
            drillDef1: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, colDefs, drillDef1).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            seriesCol: String,
            colDefs: List[(String, Any)],
            drillDef1: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, seriesCol, colDefs, drillDef1).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            colDefs: List[(String, Any)],
            drillDef1: List[(String, Any)],
            drillDef2: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, colDefs, drillDef1, drillDef2).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            seriesCol: String,
            colDefs: List[(String, Any)],
            drillDef1: List[(String, Any)],
            drillDef2: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, seriesCol, colDefs, drillDef1, drillDef2).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            colDefs: List[(String, Any)],
            drillDef1: List[(String, Any)],
            drillDef2: List[(String, Any)],
            drillDef3: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, colDefs, drillDef1, drillDef2, drillDef3).options(options.toList).plot()
  }

  def apply(dataFrame: DataFrame,
            seriesCol: String,
            colDefs: List[(String, Any)],
            drillDef1: List[(String, Any)],
            drillDef2: List[(String, Any)],
            drillDef3: List[(String, Any)],
            options: BaseModel*) = {
    convert(dataFrame, seriesCol, colDefs, drillDef1, drillDef2, drillDef3).options(options.toList).plot()
  }
}
