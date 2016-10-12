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
import com.knockdata.spark.highcharts.model.Chart
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.execution.streaming.{MemoryStream, Sink}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.sources.StreamSinkProvider
import org.apache.spark.sql.streaming.{OutputMode, ProcessingTime}
import org.junit.Test
import org.junit.Before
import org.scalatest.FunSuite



// # Bar Chart Demo
//
// Based on [Bar Basic Demo](http://www.highcharts.com/demo/bar-basic)
//
class DemoStructureStreaming {

  @Before
  def before: Unit ={
    SparkEnv.clearCheckpointDir
  }


  val spark = SparkEnv.spark
  import spark.implicits._
  implicit val ctx = spark.sqlContext

  @Test
  def testCustomSink: Unit = {
    val input = MemoryStream[String]
    val doubled = input.toDS().map(x => x + " " + x)

//    input.addData(List("hi", "holden", "bye", "pandas"))
    input.addData("hi")
    val query = doubled.writeStream
      .format(classOf[CustomSinkProvider].getCanonicalName)
      .start()


    input.addData(List("hello", "world"))
    query.processAllAvailable()

    println(SparkEnv.spark)
  }


  @Test
  def testCustomSinkWithoutInitData: Unit = {
    val input = MemoryStream[String]
    val doubled = input.toDS().map(x => x + " " + x)

    val query = doubled.writeStream
      .format(classOf[CustomSinkProvider].getCanonicalName)
      .start()


    input.addData(List("hello", "world"))
    query.processAllAvailable()

    println(SparkEnv.spark)
  }


  @Test
  def testMemorySink: Unit = {

    val input = MemoryStream[String]
    val doubled = input.toDS().map(x => x + " " + x)

    input.addData("init")
    val query = doubled.writeStream
      .format("memory")
      .queryName("memStream")
      .start()

    input.addData(List("hi", "holden", "bye", "pandas"))

    query.processAllAvailable()
    query.stop()

    spark.table("memStream").show(5)
  }

  @Test
  def testMemorySinkWithoutInit: Unit = {

    val input = MemoryStream[String]
    val doubled = input.toDS().map(x => x + " " + x)

    val query = doubled.writeStream
      .format("memory")
      .queryName("memStream")
      .start()

    input.addData(List("hi", "holden", "bye", "pandas"))

    query.processAllAvailable()
    query.stop()

    spark.table("memStream").show(5)
  }
}
