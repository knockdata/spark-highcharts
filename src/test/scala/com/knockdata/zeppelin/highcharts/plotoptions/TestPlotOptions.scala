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

package com.knockdata.zeppelin.highcharts.plotoptions

import com.knockdata.zeppelin.highcharts.AbstractTestCase

import org.junit.Test

class TestPlotOptions extends AbstractTestCase{
  @Test
  def testCodeInPlot = {
    val options = new Area()//.fillColor("linearGradient", Map("x1"->0,"y1"->0, "x2"->0, "y2"->1))
    .fillColorStops((0, "Highcharts.getOptions().colors[0]"),
        (1, "Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')"))

    val expected =
      """
        |{
        |  "fillColor":{
        |    "stops":[[0,"--code-FEA24034CBC777B5F8EC1B3125E2BFC7--"],[1,"--code-7619821E4C9B6D7CA8090F17507C6024--"]]
        |  }
        |}
      """.stripMargin

    assertEqualJson(expected, options.json)
  }
}
