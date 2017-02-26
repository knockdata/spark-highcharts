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


// # Heatmap demo
//
// Based on [Heatmap Demo](http://www.highcharts.com/demo/heatmap)
//
class DemoHeatmap {

  val bank = DataSet.dfBank
  val sqlContext = SparkEnv.sqlContext
  import sqlContext.implicits._

  // ## Heatmap Air Quality dayOfWeek, hour on particles
  //
  // Based on [Heatmap Demo](http://www.highcharts.com/demo/heatmap)
  //
  //
  // an heapmap with
  //
  // * x axis data from column $"dayOfWeek"
  // * y axis data from column $"hour"
  // * value from column $"particle"
  //
  @Test
  def demoHeatmap(): Unit = {
    val airQuality = Seq((1,0,49.44268774703557),
      (1,1,50.32411067193676),
      (1,2,50.00395256916996),
      (1,3,50.1),
      (1,4,49.17131474103586),
      (1,5,49.66533864541832),
      (1,6,51.61507936507937),
      (1,7,52.682539682539684),
      (1,8,53.62845849802372),
      (1,9,52.36904761904762),
      (1,10,51.54838709677419),
      (1,11,50.104),
      (1,12,49.76190476190476),
      (1,13,49.645418326693225),
      (1,14,49.56573705179283),
      (1,15,49.268),
      (1,16,50.38492063492063),
      (1,17,51.3794466403162),
      (1,18,52.87007874015748),
      (1,19,54.968503937007874),
      (1,20,54.98031496062992),
      (1,21,53.43700787401575),
      (1,22,52.72834645669291),
      (1,23,51.544),
      (2,0,50.03557312252964),
      (2,1,50.391304347826086),
      (2,2,49.5098814229249),
      (2,3,49.01587301587302),
      (2,4,49.13385826771653),
      (2,5,49.91699604743083),
      (2,6,50.40157480314961),
      (2,7,52.44705882352941),
      (2,8,54.14342629482072),
      (2,9,54.55378486055777),
      (2,10,53.604),
      (2,11,53.244897959183675),
      (2,12,52.125),
      (2,13,51.60956175298805),
      (2,14,51.416666666666664),
      (2,15,52.84860557768924),
      (2,16,54.152),
      (2,17,54.51984126984127),
      (2,18,56.237154150197625),
      (2,19,56.95275590551181),
      (2,20,56.904761904761905),
      (2,21,55.12698412698413),
      (2,22,53.67460317460318),
      (2,23,53.097165991902834),
      (3,0,51.976),
      (3,1,51.967871485943775),
      (3,2,51.41035856573705),
      (3,3,50.756972111553786),
      (3,4,49.96825396825397),
      (3,5,49.86507936507937),
      (3,6,52.832669322709165),
      (3,7,50.43426294820717),
      (3,8,51.53815261044177),
      (3,9,50.756),
      (3,10,49.701195219123505),
      (3,11,48.567460317460316),
      (3,12,50.21513944223108),
      (3,13,49.0),
      (3,14,48.35918367346939),
      (3,15,50.92741935483871),
      (3,16,47.572),
      (3,17,48.447154471544714),
      (3,18,50.325301204819276),
      (3,19,51.21686746987952),
      (3,20,51.265060240963855),
      (3,21,50.18473895582329),
      (3,22,49.20564516129032),
      (3,23,47.98780487804878),
      (4,0,46.987854251012145),
      (4,1,47.05622489959839),
      (4,2,47.27049180327869),
      (4,3,48.535714285714285),
      (4,4,48.53252032520325),
      (4,5,48.5650406504065),
      (4,6,50.01606425702811),
      (4,7,50.48995983935743),
      (4,8,51.12903225806452),
      (4,9,52.05714285714286),
      (4,10,52.614754098360656),
      (4,11,51.00408163265306),
      (4,12,51.421052631578945),
      (4,13,50.951219512195124),
      (4,14,50.90983606557377),
      (4,15,51.80566801619433),
      (4,16,53.10162601626016),
      (4,17,55.068825910931174),
      (4,18,56.200803212851405),
      (4,19,58.31578947368421),
      (4,20,57.725806451612904),
      (4,21,55.874493927125506),
      (4,22,53.36842105263158),
      (4,23,52.00411522633745),
      (5,0,51.05),
      (5,1,53.52697095435685),
      (5,2,50.260330578512395),
      (5,3,50.704166666666666),
      (5,4,51.52100840336134),
      (5,5,52.045833333333334),
      (5,6,54.05833333333333),
      (5,7,55.2655601659751),
      (5,8,56.09349593495935),
      (5,9,54.75903614457831),
      (5,10,52.78542510121458),
      (5,11,51.170731707317074),
      (5,12,51.04435483870968),
      (5,13,51.104838709677416),
      (5,14,48.75303643724696),
      (5,15,48.16532258064516),
      (5,16,48.714285714285715),
      (5,17,49.34126984126984),
      (5,18,50.20717131474104),
      (5,19,51.458498023715414),
      (5,20,51.8102766798419),
      (5,21,49.96812749003984),
      (5,22,48.8714859437751),
      (5,23,47.506024096385545),
      (6,0,47.324),
      (6,1,47.096385542168676),
      (6,2,47.475806451612904),
      (6,3,46.892),
      (6,4,47.29435483870968),
      (6,5,46.61904761904762),
      (6,6,47.688),
      (6,7,48.99203187250996),
      (6,8,50.12449799196787),
      (6,9,50.103585657370516),
      (6,10,49.008230452674894),
      (6,11,47.15447154471545),
      (6,12,48.31983805668016),
      (6,13,48.04435483870968),
      (6,14,47.85140562248996),
      (6,15,47.63157894736842),
      (6,16,47.730923694779115),
      (6,17,48.708),
      (6,18,50.94758064516129),
      (6,19,52.844621513944226),
      (6,20,53.388888888888886),
      (6,21,52.72222222222222),
      (6,22,52.56),
      (6,23,52.902834008097166),
      (7,0,50.94820717131474),
      (7,1,50.935222672064775),
      (7,2,51.0),
      (7,3,51.58870967741935),
      (7,4,52.08),
      (7,5,52.060483870967744),
      (7,6,52.264),
      (7,7,54.221774193548384),
      (7,8,54.74898785425101),
      (7,9,55.17479674796748),
      (7,10,54.62139917695473),
      (7,11,53.74390243902439),
      (7,12,52.734939759036145),
      (7,13,51.55421686746988),
      (7,14,51.125506072874494),
      (7,15,51.25609756097561),
      (7,16,53.248),
      (7,17,54.64372469635627),
      (7,18,54.204),
      (7,19,54.952),
      (7,20,54.46),
      (7,21,54.004),
      (7,22,51.76984126984127),
      (7,23,51.35222672064777))

    val dataFrame = airQuality.toDF("dayOfWeek", "hour", "particle")

    val d3Green = "#2ca02c"
    val d3Orange = "#ff7f0e"
    val d3LightGreen = "#98df8a"
    val d3Red = "#d62728"

    val scale = Seq(d3LightGreen, d3Green, d3Orange, d3Red)
    val stops = List(Seq(0, scale(0)), Seq(0.3, scale(1)), Seq(0.5, scale(2)), Seq(1, scale(3)))
    val chart = highcharts(dataFrame
      .series("x" -> "dayOfWeek", "y" -> "hour", "value" -> "particle").orderBy($"dayOfWeek", $"hour")
    ).chart(Chart.heatmap).colorAxis(new ColorAxis().stops(stops))

    chart.plot()

    new PrintWriter(s"target/demoBasicArea.json") { write(chart.replaced); close }

  }

  // ## Heatmap Sales per Employee weekday
  //
  // Based on [Heatmap Demo](http://www.highcharts.com/demo/heatmap)
  //
  //
  // an heapmap with
  //
  // * x axis data from column $"employee"
  // * y axis data from column $"data"
  // * value from column $"sales"
  //
  @Test
  def demoSalesPerEmployeePerWeekday: Unit = {
    val dataset = Seq(
      (0, 0, 10), (0, 1, 19), (0, 2, 8), (0, 3, 24), (0, 4, 67),
      (1, 0, 92), (1, 1, 58), (1, 2, 78), (1, 3, 117), (1, 4, 48),
      (2, 0, 35), (2, 1, 15), (2, 2, 123), (2, 3, 64), (2, 4, 52),
      (3, 0, 72), (3, 1, 132), (3, 2, 114), (3, 3, 19), (3, 4, 16),
      (4, 0, 38), (4, 1, 5), (4, 2, 8), (4, 3, 117), (4, 4, 115),
      (5, 0, 88), (5, 1, 32), (5, 2, 12), (5, 3, 6), (5, 4, 120),
      (6, 0, 13), (6, 1, 44), (6, 2, 88), (6, 3, 98), (6, 4, 96),
      (7, 0, 31), (7, 1, 1), (7, 2, 82), (7, 3, 32), (7, 4, 30),
      (8, 0, 85), (8, 1, 97), (8, 2, 123), (8, 3, 64), (8, 4, 84),
      (9, 0, 47), (9, 1, 114), (9, 2, 31), (9, 3, 48), (9, 4, 91))

    val dataFrame = dataset.toDF("employee", "day", "sales")

    val chart = highcharts(dataFrame.
      series("x" -> "employee", "y" -> "day", "value" -> "sales").
      orderBy($"employee", $"day")
    ).chart(Chart.heatmap).colorAxis(
      ColorAxis.colorRange("#FFFFFF", "#7cb5ec").min(0))

    chart.xAxis(new XAxis().categories("Alexander", "Marie", "Maximilian", "Sophia",
      "Lukas", "Maria", "Leon", "Anna", "Tim", "Laura"))

    chart.yAxis(new YAxis().categories("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"))

    chart.legend(new Legend().align("right").layout("vertical"))

    chart.plot()
  }
}
