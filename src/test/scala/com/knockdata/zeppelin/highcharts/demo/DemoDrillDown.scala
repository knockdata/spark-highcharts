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

// # Drilldown Demo
//
// Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)
//
class DemoDrillDown {
  val bank = DataSet.dfBank

  // ## Drilldown Basic
  //
  // Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)
  //
  // A line chart with
  //
  // * x axis data from column $"marital"
  // * y axis aggregated the average balance
  //
  // Then it drilldown to
  //
  // * x axis data from column $"job"
  // * y axis aggregated the average balance
  @Test
  def demoDrilldownBasic: Unit = {
    highcharts(bank
      .series("name" -> "marital",
        "y" -> avg(col("balance")))
      .drilldown("name" -> "job",
        "y" -> avg(col("balance"))))
      .chart(Chart.column)
      .plot()
  }

  // ## Drilldown 2 Levels
  //
  // Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)
  //
  // A line chart with
  //
  // * x axis data from column $"marital"
  // * y axis aggregated the average balance
  //
  // Then it drilldown to
  //
  // * x axis data from column $"job"
  // * y axis aggregated the average balance
  //
  // Then it drill down to
  //
  // * x axis data from column $"education"
  // * y axis aggregated the max balance
  //
  // with 3 levels, the output is pretty big
  // number of data point is
  // size(marital) + size(marital) * size(balance)
  //   + size(marital) * size(balance) + size(education)
  @Test
  def demoDrilldown2Level: Unit = {

    highcharts(bank
      .series("name" -> "marital",
        "y" -> avg(col("balance")))
      .drilldown("name" -> "job",
        "y" -> avg(col("balance")))
      .drilldown("name" -> "education",
        "y" -> max(col("balance"))))
      .chart(Chart.column)
      .plot()


  }

  // ## Drilldown Multiple Series Chart
  //
  // Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)
  //
  // A line chart with
  //
  // * multiple series from column $"marital"
  // * x axis data from column $"job"
  // * y axis aggregated the average balance
  //
  // Then it drill down to
  //
  // * x axis data from column $"education"
  // * y axis aggregated the max balance
  //
  // series with one level drilldown, the output is pretty big
  // number of data point is
  // size(marital) + size(marital) * size(balance)
  //   + size(marital) * size(balance) + size(education)
  @Test
  def demoLineBasicDesc: Unit = {

    highcharts(bank
      .seriesCol("marital")
      .series("name" -> "job",
        "y" -> avg(col("balance")))
      .drilldown("name" -> "education",
        "y" -> avg(col("balance"))))
      .plot()
  }

}
