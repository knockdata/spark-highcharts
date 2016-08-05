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

// # Column Chart Demo
//
// Based on [Column Basic Demo](http://www.highcharts.com/demo/column-basic)
//
class DemoColumnChart {
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
    highcharts(
      bank
        .series("x" -> "age", "y" -> count("*"))
        .orderBy(col("age"))
      )
      .chart(Chart.column)
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

    val john = Seq(5, 3, 4, 7, 2).map(v => ("John", v))
    val jane = Seq(2, 2, 3, 2, 1).map(v => ("Jane", v))
    val joe = Seq(3, 4, 4, 2, 5).map(v => ("Jeo", v))

    val dataFrame = (john ++ jane ++ joe).toDF("name", "consumption")

    highcharts(
      dataFrame
        .seriesCol("name")
        .series("y" -> "consumption"))
      .chart(Chart.column)
      .xAxis(XAxis("").categories("Apples", "Oranges", "Pears", "Grapes", "Bananas"))
      .plotOptions(PlotOptions.column.stacking("normal"))
      .plot()
  }
}
