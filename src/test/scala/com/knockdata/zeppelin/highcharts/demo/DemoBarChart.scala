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
import com.knockdata.zeppelin.highcharts.model.{Chart, XAxis}
import org.apache.spark.sql.functions._
import org.junit.Test

// # Bar Chart Demo
//
// Based on [Bar Basic Demo](http://www.highcharts.com/demo/bar-basic)
//
class DemoBarChart {
  val sqlContext = SparkEnv.sqlContext

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
      .plotOptions(PlotOptions.column.groupPadding(0).pointPadding(0).borderWidth(0))
      .plot()
  }

  // ## Stacked Column
  //
  // Based on [Stacked Column](http://www.highcharts.com/demo/column-stacked)
  //
  // Column are stacked, each stack is one series which is person
  //
  // * x axis is index of fruit types. it does not specified by in data series
  // * y from $"consumption"
  //
  @Test
  def demoStackedColumn: Unit = {
    import sqlContext.implicits._

    val male = Seq(-2.2, -2.2, -2.3, -2.5, -2.7, -3.1, -3.2,
      -3.0, -3.2, -4.3, -4.4, -3.6, -3.1, -2.4,
      -2.5, -2.3, -1.2, -0.6, -0.2, -0.0, -0.0).map(v => ("Male", v))
    val female = Seq(2.1, 2.0, 2.2, 2.4, 2.6, 3.0, 3.1, 2.9,
      3.1, 4.1, 4.3, 3.6, 3.4, 2.6, 2.9, 2.9,
      1.8, 1.2, 0.6, 0.1, 0.0).map(v => ("Female", v))

    val categories = List("0-4", "5-9", "10-14", "15-19",
    "20-24", "25-29", "30-34", "35-39", "40-44",
    "45-49", "50-54", "55-59", "60-64", "65-69",
    "70-74", "75-79", "80-84", "85-89", "90-94",
    "95-99", "100 + ")

    val dataFrame = (male ++ female).toDF("gender", "population")

    highcharts(dataFrame)
      .chart(Chart.bar)
      .seriesCol("gender")
      .series("y" -> "population")
      .xAxis(XAxis("").categories(categories))
      .xAxis(XAxis("").categories(categories).opposite(true).linkedTo(0))
      .plotOptions(PlotOptions.series.stacking("normal"))
      .plot()
  }
}
