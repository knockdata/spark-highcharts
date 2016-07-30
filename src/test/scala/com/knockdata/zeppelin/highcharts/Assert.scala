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

import base.BaseModel
import net.liftweb.json._
import org.junit.Assert._
import org.skyscreamer.jsonassert.{JSONAssert, JSONCompare, JSONCompareMode, JSONCompareResult}

object Assert {
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
    val result = JSONCompare.compareJSON(expected, actual, compareMode);
    if(result.failed()) {
      val msg = s"actual \n$actual \n!= expected \n$expected"
      println(msg)
      throw new AssertionError(result.getMessage());
    }
    JSONAssert.assertEquals(expected, actual, false)
  }

  def assertEqualJson(expected: String, actual: BaseModel): Unit = {
    assertEqualJson(expected, actual.result)
  }


  def assertEqualJson(expected: String, actualJson: JValue): Unit = {
    assertEqualJson(expected, pretty(render(actualJson)))
  }


  def assertEqualJson(expectedJson: JValue, actualJson: JValue): Unit = {
    assertEqualJson(pretty(render(expectedJson)), pretty(render(actualJson)))
  }

}
