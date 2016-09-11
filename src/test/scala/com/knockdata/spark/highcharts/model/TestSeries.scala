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

package com.knockdata.spark.highcharts.model

import com.knockdata.spark.highcharts.AbstractTestCase
import org.junit.Test

class TestSeries extends AbstractTestCase{
  @Test
  def testSeriesListInt(): Unit = {
    val expected =
      """
        |{
        |  "data":[0,5,3,5],
        |  "name":"x"
        |}
      """.stripMargin
    val x = Series(0, 5, 3, 5)
    x("name", "x")
    assertEqualJson(expected, x)
  }

  @Test
  def testSeriesListPair(): Unit = {
    val expected = """
                  |{
                  |  "data":[[5, 2], [6, 3], [8, 2]],
                  |  "name":"x"
                  |}""".stripMargin

    val x = Series(

        (5,2),
        (6, 3),
        (8, 2)
      )
    x("name", "x")
    assertEqualJson(expected, x)

  }

//  @Test
//  def testSeriesListList: Unit = {
//    val x = Series(
//      List(
//        List(1,2),
//        List(2, 3)
//      ))
//    x("name", "x")
//    println(x.toString)
//  }

  @Test
  def testSeriesListNamedValue(): Unit = {
    val expected =
      """
        |{
        |  "data":[{
        |    "name":"Point 1",
        |    "color":"#00FF00",
        |    "y":1
        |  },{
        |    "name":"Point21",
        |    "color":"#FF00FF",
        |    "y":2
        |  }],
        |  "name":"x"
        |}
      """.stripMargin

    val x = Series(

        Map("name" -> "Point 1", "color" -> "#00FF00", "y" -> 1),
        Map("name" -> "Point21", "color" -> "#FF00FF", "y" -> 2)
      )
    x("name", "x")

    assertEqualJson(expected, x)

  }

  @Test
  def testSeriesListMarker(): Unit = {
    val expected =
      """
        |{
        |  "name":"x",
        |  "data":[29.9,71.5,106.4,129.2,144,176,135.6,148.5,216.4,194.1,95.6,54.4]
        |}
      """.stripMargin

    val x = Series(
      29.9, 71.5, 106.4, 129.2, 144.0,
        176.0, 135.6, 148.5, 216.4, 194.1,
        95.6, 54.4)
    x("name", "x")


    assertEqualJson(expected, x)

  }

}
