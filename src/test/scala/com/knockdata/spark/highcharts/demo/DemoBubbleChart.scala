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
import com.knockdata.spark.highcharts.model.{Chart, Highcharts, Series, Tooltip, XAxis, YAxis}

import org.junit.Test

// # Pie Chart Demo
//
// Based on [Pie Chart Demo](http://www.highcharts.com/demo/pie-basic)
//
class DemoBubbleChart {
  val sqlContext = SparkEnv.sqlContext
  import sqlContext.implicits._
  val bank = DataSet.dfBank

//  // ## Histogram
//  //
//  // Based on [plot-histograms-in-highcharts](http://stackoverflow.com/questions/18042165/plot-histograms-in-highcharts)
//  //
//  // an line chart with
//  //
//  // * x axis data from column $"age"
//  // * y axis number of record for age
//  // * data point order by age
//  //

//  @Test
//  def demoBubble(): Unit = {
//
//    val dataFrame = Seq(
//      (95.0, 95.0, 13.8, "BE", "Belgium" ),
//      (86.5, 102.9, 14.7, "DE", "Germany" ),
//      (80.8, 91.5, 15.8, "FI", "Finland" ),
//      (80.4, 102.5, 12.0, "NL", "Netherlands" ),
//      (80.3, 86.1, 11.8, "SE", "Sweden" ),
//      (78.4, 70.1, 16.6, "ES", "Spain" ),
//      (74.2, 68.5, 14.5, "FR", "France" ),
//      (73.5, 83.1, 10.0, "NO", "Norway" ),
//      (71.0, 93.2, 24.7, "UK", "United Kingdom" ),
//      (69.2, 57.6, 10.4, "IT", "Italy" ),
//      (68.6, 20.0, 16.0, "RU", "Russia" ),
//      (65.5, 126.4, 35.3, "US", "United States" ),
//      (65.4, 50.8, 28.5, "HU", "Hungary" ),
//      (63.4, 51.8, 15.4, "PT", "Portugal" ),
//      (64.0, 82.9, 31.3, "NZ", "New Zealand")
//    ).toDF("fat intake", "sugar intake", "obesity", "country code", "country")
//
//
//    val chart = highcharts(dataFrame
//      .series(
//        "x" -> "fat intake",
//        "y" -> "sugar intake",
//        "z" -> "obesity",
//        "name" -> "country code",
//        "country" -> "country"))
//      .chart(Chart.bubble)
//      .plotOptions(PlotOptions.bubble.dataLabels("enabled" -> true, "format" -> "{point.name}"))
//      .xAxis(XAxis("fat intake daily"))
//      .yAxis(YAxis("sugar intake daily")
//        .plotLine(
//          "dashStyle" -> "dot",
//          "color" -> "black",
//          "value" -> 50,
//          "width" -> 3,
//          "label" -> Map("text" -> "Safe sugar intake 50g/day", "align" -> "right"),
//          "zIndex" -> 3)
//      )
//      .tooltip(new Tooltip().pointFormat("{point.country}"))
//
//    chart.plot()
//    new PrintWriter("target/demoBubble.json") { write(chart.replaced); close }
//  }
}
