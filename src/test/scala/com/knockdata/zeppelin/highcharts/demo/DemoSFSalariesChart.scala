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

import java.io.PrintWriter

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model.{Chart, XAxis}
import org.apache.spark.sql.functions._
import org.junit.Test

// # San Fransisco Salaries Demo
//
// Dataset from [Kaggle](https://www.kaggle.com/kaggle/sf-salaries)
//
class DemoSFSalariesChart {
  val sqlContext = SparkEnv.sqlContext
  import sqlContext.implicits._



  // ## SF salaries
  //
  // Dataset from [Kaggle](https://www.kaggle.com/kaggle/sf-salaries)
  //
  // take top 10 payment job
  //
  // * x job title
  // * y avg base pay
  // * data point order by avg base pay descending
  //
  @Test
  def demoSFSalaries(): Unit = {


    val file = "src/test/resources/SF-Salaries.csv"
    val dataFrame = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true") // Use first line of all files as header
      .option("inferSchema", "true") // Automatically infer data types
      .load(file)

    val chart = highcharts(dataFrame
      .series("name" -> "JobTitle", "y" -> avg($"BasePay"))
      .orderBy(avg($"BasePay").desc)
      .take(10))
      .chart(Chart.bar)

    chart.plot()

    new PrintWriter("target/demoSFSalaries.json") { write(chart.replaced); close }
  }

}
