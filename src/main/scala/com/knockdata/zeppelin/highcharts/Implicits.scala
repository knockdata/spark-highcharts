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

import org.apache.spark.sql.DataFrame

import model._

import scala.language.implicitConversions

object Implicits {
  implicit def dataFrameNoDrilldown2Highcharts(defs:
                                               (DataFrame,
                                                 List[(String, Any)])): Highcharts = {
    val (rootDataFrame, colDefs) = defs
    convert(rootDataFrame, colDefs)
  }


  implicit def dataFrame1Drilldown2Highcharts(defs:
                                              (DataFrame,
                                                List[(String, Any)],
                                                List[(String, Any)])): Highcharts = {
    val (rootDataFrame, colDefs, drillDef1) = defs
    convert(rootDataFrame, colDefs, drillDef1)
  }

  implicit def dataFrame2Drilldown2Highcharts(defs:
                                              (DataFrame,
                                                List[(String, Any)],
                                                List[(String, Any)],
                                                List[(String, Any)])): Highcharts = {
    val (rootDataFrame, colDefs, drillDef1, drillDef2) = defs
    convert(rootDataFrame, colDefs, drillDef1, drillDef2)
  }

  implicit def dataFrame3Drilldown2Highcharts(defs:
                                              (DataFrame,
                                                List[(String, Any)],
                                                List[(String, Any)],
                                                List[(String, Any)],
                                                List[(String, Any)])): Highcharts = {
    val (rootDataFrame, colDefs, drillDef1, drillDef2, drillDef3) = defs
    convert(rootDataFrame, colDefs, drillDef1, drillDef2, drillDef3)
  }

  implicit def dataFrame4Drilldown2Highcharts(defs:
                                              (DataFrame,
                                                List[(String, Any)],
                                                List[(String, Any)],
                                                List[(String, Any)],
                                                List[(String, Any)],
                                                List[(String, Any)])): Highcharts = {
    val (rootDataFrame, colDefs, drillDef1, drillDef2, drillDef3, drillDef4) = defs
    convert(rootDataFrame, colDefs, drillDef1, drillDef2, drillDef3, drillDef4)
  }


  implicit def dataFrameSeriesNoDrilldown2Highcharts(defs:
                                                     (DataFrame,
                                                       String,
                                                       List[(String, Any)])): Highcharts = {
    val (rootDataFrame, seriesCol, colDefs) = defs
    convert(rootDataFrame, seriesCol, colDefs)
  }


  implicit def dataFrameSeries1Drilldown2Highcharts(defs:
                                                    (DataFrame,
                                                      String,
                                                      List[(String, Any)],
                                                      List[(String, Any)])): Highcharts = {
    val (rootDataFrame, seriesCol, colDefs, drillDef1) = defs
    convert(rootDataFrame, seriesCol, colDefs, drillDef1)
  }

  implicit def dataFrameSeries2Drilldown2Highcharts(defs:
                                                    (DataFrame,
                                                      String,
                                                      List[(String, Any)],
                                                      List[(String, Any)],
                                                      List[(String, Any)])): Highcharts = {
    val (rootDataFrame, seriesCol, colDefs, drillDef1, drillDef2) = defs
    convert(rootDataFrame, colDefs, drillDef1, drillDef2)
  }

  implicit def dataFrameSeries3Drilldown2Highcharts(defs:
                                                    (DataFrame,
                                                      String,
                                                      List[(String, Any)],
                                                      List[(String, Any)],
                                                      List[(String, Any)],
                                                      List[(String, Any)])): Highcharts = {
    val (rootDataFrame, seriesCol, colDefs, drillDef1, drillDef2, drillDef3) = defs
    convert(rootDataFrame, seriesCol, colDefs, drillDef1, drillDef2, drillDef3)
  }

  implicit def dataFrameSeries4Drilldown2Highcharts(defs:
                                                    (DataFrame,
                                                      String,
                                                      List[(String, Any)],
                                                      List[(String, Any)],
                                                      List[(String, Any)],
                                                      List[(String, Any)],
                                                      List[(String, Any)])): Highcharts = {
    val (rootDataFrame, seriesCol, colDefs, drillDef1, drillDef2, drillDef3, drillDef4) = defs
    convert(rootDataFrame, seriesCol, colDefs, drillDef1, drillDef2, drillDef3, drillDef4)
  }

  implicit def seriesToDrilldown(series: List[Series]) = new Drilldown(series)
}
