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
import com.knockdata.zeppelin.highcharts.model.{Chart, Highcharts, Series, XAxis}
import org.apache.spark.sql.functions._
import org.junit.Test

// # Pie Chart Demo
//
// Based on [Pie Chart Demo](http://www.highcharts.com/demo/pie-basic)
//
class DemoPieChart {
  val sqlContext = SparkEnv.sqlContext

  val bank = DataSet.dfBank

  // ## Donut Chart
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
  def demoDonut: Unit = {
    import sqlContext.implicits._
    import org.apache.spark.sql.functions._

    val dataFrame = Seq(
      ("Microsoft Internet Explorer", "v11.0", 24.13),
      ("Microsoft Internet Explorer", "v10.0", 5.33),
      ("Microsoft Internet Explorer", "v9.0", 8.11),
      ("Microsoft Internet Explorer", "v8.0", 17.2),
      ("Microsoft Internet Explorer", "v7.0", 0.5),
      ("Microsoft Internet Explorer", "v6.0", 1.06),
      ("Chrome", "v43.0", 1.45),
      ("Chrome", "v42.0", 3.68),
      ("Chrome", "v41.0", 4.32),
      ("Chrome", "v40.0", 5.0),
      ("Chrome", "v39.0", 2.96),
      ("Chrome", "v38.0", 0.6),
      ("Chrome", "v36.0", 2.53),
      ("Chrome", "v37.0", 0.38),
      ("Chrome", "v35.0", 0.85),
      ("Chrome", "v34.0", 0.14),
      ("Chrome", "v33.0", 0.19),
      ("Chrome", "v32.0", 0.55),
      ("Chrome", "v31.0", 1.24),
      ("Chrome", "v30.0", 0.14),
      ("Firefox", "v38.0", 1.02),
      ("Firefox", "v37.0", 2.31),
      ("Firefox", "v36.0", 2.32),
      ("Firefox", "v35.0", 2.76),
      ("Firefox", "v34.0", 1.27),
      ("Firefox", "v33.0", 0.22),
      ("Firefox", "v32.0", 0.15),
      ("Firefox", "v31.0", 0.22),
      ("Safari", "v8.0", 2.56),
      ("Safari", "v7.1", 0.77),
      ("Safari", "v7.0", 0.26),
      ("Safari", "v6.2", 0.17),
      ("Safari", "v6.1", 0.29),
      ("Safari", "v5.1", 0.42),
      ("Safari", "v5.0", 0.3),
      ("Opera", "v29", 0.16),
      ("Opera", "v28", 0.24),
      ("Opera", "v27", 0.17),
      ("Opera", "v12.x", 0.34)
    ).map{
      case (b, v, s) => (b, b + " " + v, s)
    }.toDF("browser", "version", "share")

    val seriesBrowser = Series(dataFrame,
      "name" -> "browser",
      "y" -> sum(col("share")),
      "orderBy" -> col("browser"))
      .size("60%")
      .dataLabels(
        "distance" -> -30,
        "formatter" ->
          """
            |function() {
            |  return this.y > 1 ? this.point.name : null;
            |}
          """.stripMargin)

    val seriesVersion = Series(dataFrame,
      "name" -> "version",
      "y" -> "share",
      "orderBy" -> col("browser"))
      .size("80%")
      .innerSize("60%")
      .dataLabels("formatter" ->
        """
          |function() {
          |  return this.y > 1 ? this.point.name : null;
          |}
        """.stripMargin)

    val chart = new Highcharts(seriesBrowser, seriesVersion)
      .chart(Chart.pie)

    chart.plot()
//
//    highcharts(bank)
//      .chart(Chart.column)
//      .series("x" -> "age", "y" -> count("*"))
//      .orderBy(col("age"))
//      .plotOptions(PlotOptions.column.groupPadding(0).pointPadding(0).borderWidth(0))
//      .plot()
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
