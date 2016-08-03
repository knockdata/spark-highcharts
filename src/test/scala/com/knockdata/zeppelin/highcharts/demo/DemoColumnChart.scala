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

package com.knockdata.zeppelin.highcharts.demo

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model.Chart
import org.apache.spark.sql.functions._
import org.junit.Test

// # Column Chart Demo
//
// Based on [Column Basic Demo](http://www.highcharts.com/demo/column-basic)
//
class DemoColumnChart {
  val bank = DataSet.dfBank

  // ## Histogram
  //
  // Based on [plot-histograms-in-highcharts](http://stackoverflow.com/questions/18042165/plot-histograms-in-highcharts)
  //
  // an line chart with
  //
  // * x axis data from column $"age"
  // * y axis number of record for age
  // * data point order by age
  //
  @Test
  def demoHistogram: Unit = {
    highcharts(bank)
      .chart(Chart.column)
      .series("x" -> "age", "y" -> count("*"))
      .orderBy(col("age"))
      .plotOptions(new plotOptions.Column().groupPadding(0).pointPadding(0).borderWidth(0))
      .plot()
  }
}
