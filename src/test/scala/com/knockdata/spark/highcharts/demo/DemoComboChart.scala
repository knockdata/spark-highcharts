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

package com.knockdata.spark.highcharts.demo

import java.io.PrintWriter

import com.knockdata.spark.highcharts._
import com.knockdata.spark.highcharts.model._
import org.apache.spark.sql.functions._
import org.junit.Test


// # Combo Chart
//
// Based on [Multiple Axis](http://www.highcharts.com/demo/combo-multi-axes)
//
class DemoComboChart {

  val bank = DataSet.dfBank
  val sqlContext = SparkEnv.sqlContext
  import sqlContext.implicits._

  // ## Basic Area
  //
  // Based on [Area Basic Demo](http://www.highcharts.com/demo/area-basic)
  //
  //
  // an line chart with
  //
  // * x axis data from column $"age"
  // * y axis number of record for age
  // * data point order by age
  //
  @Test
  def demoComboChart(): Unit = {

    val months = Seq("Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val rainfall = Seq(49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4)
    val seaLevelPressure=Seq(1016, 1016, 1015.9, 1015.5, 1012.3, 1009.5, 1009.6, 1010.2, 1013.1, 1016.9, 1018.2, 1016.7)
    val temperature = Seq(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)

    val dataFrame = months.zipWithIndex.
      map{case (month, i) => (month, rainfall(i), seaLevelPressure(i), temperature(i))}.
      toDF("month", "rainfall", "pressure", "temperature")


    val seriesRainfall = dataFrame.series("name" -> "month", "y" -> "rainfall").set("type", "column")
    val seriesPressure = dataFrame.series("name" -> "month", "y" -> "pressure").set("type", "spline")
    val seriesTemperature = dataFrame.series("name" -> "month", "y" -> "temperature").set("type", "spline")

    val yAxisTemprature = new YAxis("Temperature").labels("format" -> "{value}°C")
    val yAxisRainfall = new YAxis("Rainfall").labels("format" -> "{value}mm")
    val yAxisPressure = new YAxis("Pressure").labels("format" -> "{value}mm")
    val chart = highcharts(seriesRainfall, seriesPressure, seriesTemperature).yAxis(yAxisTemprature).yAxis(yAxisRainfall)

    chart.plot()

    new PrintWriter(s"target/demoBasicArea.json") { write(chart.replaced); close }

  }

  @Test
  def demoBasicAreaPlot(): Unit = {
    val USA = Seq(0, 0, 0, 0, 0, 6, 11, 32, 110, 235, 369, 640,
      1005, 1436, 2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,
      27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,
      26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,
      24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344, 23586,
      22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
      10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104).
        zip(1940 to 2006).map(p => ("USA", p._1, p._2))

    val USSR = Seq(0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      5, 25, 50, 120, 150, 200, 426, 660, 869, 1060, 1605, 2471, 3322,
      4238, 5221, 6129, 7089, 8339, 9399, 10538, 11643, 13092, 14478,
      15915, 17385, 19055, 21205, 23044, 25393, 27935, 30062, 32049,
      33952, 35804, 37431, 39197, 45000, 43000, 41000, 39000, 37000,
      35000, 33000, 31000, 29000, 27000, 25000, 24000, 23000, 22000,
      21000, 20000, 19000, 18000, 18000, 17000, 16000).
      zip(1940 to 2006).map(p => ("USSR/Russia", p._1, p._2))

    val dataFrame = (USA ++ USSR).toDF("country", "stockpile", "year")

    val title = new Title("Monthly Average Temperature").x(-20)
    val chart = highcharts(dataFrame
      .seriesCol("country")
      .series("x" -> "year", "y" -> "stockpile")
      .orderBy(col("year")))
      .chart(Chart.area).title(new Title("hello"))

    println(chart.plotData)
    chart.html(open = false)

    new PrintWriter(s"target/demoBasicAreaPlot.json") { write(chart.replaced); close }
  }

  @Test
  def testBank: Unit = {
    val chart = highcharts(bank.series("x" -> "age", "y" -> avg(col("balance"))).orderBy(col("age")))

    chart.plot()

    new PrintWriter(s"target/testBank.json") { write(chart.replaced); close }
  }

}
