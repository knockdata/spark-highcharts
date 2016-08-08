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

package com.knockdata.zeppelin.highcharts

import net.liftweb.json._
import org.json.JSONException
import org.skyscreamer.jsonassert.{JSONAssert, JSONCompare, JSONCompareMode}

trait AbstractTestCase {

  val isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean.
    getInputArguments.toString.indexOf("jdwp") >= 0

  def debug(msg: String) = {
    if (isDebug) {
      println(msg)
    }
  }

  def debug(msg: JValue) = {
    if (isDebug) {
      println(pretty(render(msg)))
    }
  }
  def parseJson(str: String): JValue = {
    try {
      parse(str)
    }
    catch {
      case ex: Exception =>
        println(s"fail to parse\n$str")
        throw ex
    }
  }

  def assertEqualJson(expected: String, actual: String): Unit = {
    val compareMode = JSONCompareMode.NON_EXTENSIBLE
    val result = try {
      JSONCompare.compareJSON(expected, actual, compareMode)
    }
    catch {
      case ex: JSONException =>
        val msg = s"actual \n$actual"
        println(msg)
        throw new AssertionError(ex.getMessage)
    }
    if(result.failed()) {
        val msg = s"actual \n$actual \n!= expected \n$expected"
        println(msg)
        throw new AssertionError(result.getMessage)
    }
      JSONAssert.assertEquals(expected, actual, false)

  }

  def assertEqualJson(expected: String, actual: base.BaseModel): Unit = {
    assertEqualJson(expected, actual.result)
  }


  def assertEqualJson(expected: String, actualJson: JValue): Unit = {
    assertEqualJson(expected, pretty(render(actualJson)))
  }


  def assertEqualJson(expectedJson: JValue, actualJson: JValue): Unit = {
    assertEqualJson(pretty(render(expectedJson)), pretty(render(actualJson)))
  }

  def assertEqual(jsonFile: String, chart: model.Highcharts): Unit = {
    val stream = scala.io.Source.fromFile(jsonFile)
    try {
      val expected = stream.getLines().mkString("\n")
      val actual = chart.json
      assertEqualJson(expected, actual)
    }
    finally {
      stream.close()
    }
  }
}
