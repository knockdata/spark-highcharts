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
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.execution.streaming.MemoryStream
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.junit.Test



// # Area Chart Demo
//
// Based on [Area Basic Demo](http://www.highcharts.com/demo/area-basic)
//
class DemoStreamingAreaChart {

  val bank = DataSet.dfBank
  implicit val sqlContext = SparkEnv.sqlContext
  import SparkEnv.spark.implicits._



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
  def demoBasicArea(): Unit = {

    val input = MemoryStream[NuclearStockpile]

    val USA = Seq(0, 0, 0, 0, 0, 6, 11, 32, 110, 235, 369, 640,
      1005, 1436, 2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,
      27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,
      26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,
      24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344, 23586,
      22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
      10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104).
        zip(1940 to 2006).map(p => NuclearStockpile("USA", p._1, p._2))

    val USSR = Seq(0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      5, 25, 50, 120, 150, 200, 426, 660, 869, 1060, 1605, 2471, 3322,
      4238, 5221, 6129, 7089, 8339, 9399, 10538, 11643, 13092, 14478,
      15915, 17385, 19055, 21205, 23044, 25393, 27935, 30062, 32049,
      33952, 35804, 37431, 39197, 45000, 43000, 41000, 39000, 37000,
      35000, 33000, 31000, 29000, 27000, 25000, 24000, 23000, 22000,
      21000, 20000, 19000, 18000, 18000, 17000, 16000).
        zip(1940 to 2006).map(p => NuclearStockpile("USSR/Russia", p._1, p._2))

    input.addData(USA.take(30))
    input.addData(USSR.take(30))
//
//
//    val query: StreamingChart = highcharts(
//      input.toDF.seriesCol("country")
//        .series("x" -> "year", "y" -> "stockpile")
//        .orderBy(col("year")))
//
//    query.processAllAvailable()
////    input.addData(USA.drop(30))
////    input.addData(USSR.drop(30))
////    query.processAllAvailable()
//
//    query.stop()

  }

}
