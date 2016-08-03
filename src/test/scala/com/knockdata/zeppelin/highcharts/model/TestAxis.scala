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

import com.knockdata.zeppelin.highcharts.AbstractTestCase
import org.junit.Test

class TestAxis extends AbstractTestCase {

  @Test
  def testLabelStyleColor: Unit = {
    val yAxis = new YAxis("Average Balance").plotBands(
      Map("from" -> 0, "to" -> 1000, "color" -> "rgba(68, 170, 213, 0.1)",
        "label" -> Map(
          "text" -> "Low",
          "style" -> Map(
            "color" -> "#606060"
          )
        )
      ),
      Map("from" -> 5000, "to" -> 10000, "color" -> "rgba(68, 170, 213, 0.1)",
        "label" -> Map(
          "text" -> "High",
          "style" -> Map(
            "color" -> "#606060"
          )
        )
      )
    )

    val expected =
      """
        |{
        |  "plotBands":[{
        |    "from":0,
        |    "to":1000,
        |    "color":"rgba(68, 170, 213, 0.1)",
        |    "label":{
        |      "text":"Low",
        |      "style":{
        |        "color":"#606060"
        |      }
        |    }
        |  },{
        |    "from":5000,
        |    "to":10000,
        |    "color":"rgba(68, 170, 213, 0.1)",
        |    "label":{
        |      "text":"High",
        |      "style":{
        |        "color":"#606060"
        |      }
        |    }
        |  }],
        |  "title":{
        |    "text":"Average Balance"
        |  }
        |}
        |
      """.stripMargin
    assertEqualJson(expected, actual = yAxis.replaced)
  }
}
