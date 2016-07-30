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

package com.knockdata.zeppelin.highcharts.model

import com.knockdata.zeppelin.highcharts.Assert
import Assert._
import org.junit.Test

class TestTitle {
    @Test
  def testEmpty: Unit = {
    val expected =
      """
        {}
      """

    val actual = new Title()
    assertEqualJson(expected, actual)
  }

  @Test
  def testX: Unit = {
    val expected =
      """
        |{
        |            "text": "Monthly Average Temperature",
        |            "x": -20
        |}

      """.stripMargin

    val actual = new Title("Monthly Average Temperature").x(-20)
    assertEqualJson(expected, actual)
  }

  @Test
  def testAlign: Unit = {
    val expected =
      """
        |{
        |            "text": "Monthly Average Temperature",
        |            "x": -20,
        |            "align":"center"
        |}

      """.stripMargin

    val actual = new Title("Monthly Average Temperature").x(-20).align("center")
    assertEqualJson(expected, actual)
  }
}
