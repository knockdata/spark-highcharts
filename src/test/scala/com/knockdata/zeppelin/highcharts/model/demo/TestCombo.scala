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

package com.knockdata.zeppelin.highcharts.model.demo

import com.knockdata.zeppelin.highcharts.model._
import com.knockdata.zeppelin.highcharts._
import org.junit.Test

/**
  *
  * @see <a href="http://www.highcharts.com/demo/area-basic">Line Basic</a>
  */
class TestCombo extends AbstractTestCase{

  @Test
  def testAreaBasic(): Unit = {
    val title = new Title("Combination chart")

    val subtitle = new Subtitle(
      """|Source: <a href="http://thebulletin.metapress.com/content/c4120650912x74k7/fulltext.pdf">
        |                thebulletin.metapress.com</a>
      """.stripMargin)

    val xAxis = new XAxis("").categories("Apples", "Oranges", "Pears", "Bananas", "Plums")

    // TODO labels items



    val seriesJane = Series(3, 2, 1, 3, 4).name("Jane")("type", "column")
    val seriesJohn = Series(2, 3, 5, 7, 6).name("John")("type", "column")
    val seriesJoe = Series(4, 3, 3, 9, 0).name("Joe")("type", "column")

    val seriesAverage = Series(3.0, 2.67, 3, 6.33, 3.33).name("Average")("type", "spline")
    val seriesTotalConsumption = Series(
      Map("name" -> "Jane", "y" -> 13),
      Map("name" -> "John", "y" -> 23),
      Map("name" -> "Joe", "y" -> 19)).typ("pie").
      center(100, 80).
      size(100).
      dataLabels("enabled" -> true)


    val chart = new Highcharts(seriesJane, seriesJohn, seriesJoe, seriesAverage,seriesTotalConsumption)
    chart.options(title,
      xAxis)

    chart.plot()

    assertEqualJson(expected, chart)
  }


  val expected =
    """
      |{
      |  "title":{
      |    "text":"Combination chart"
      |  },
      |  "xAxis":{
      |    "categories":["Apples","Oranges","Pears","Bananas","Plums"]
      |  },
      |  "series":[{
      |    "name":"Jane",
      |    "type":"column",
      |    "data":[3,2,1,3,4]
      |  },{
      |    "name":"John",
      |    "type":"column",
      |    "data":[2,3,5,7,6]
      |  },{
      |    "name":"Joe",
      |    "type":"column",
      |    "data":[4,3,3,9,0]
      |  },{
      |    "name":"Average",
      |    "type":"spline",
      |    "data":[3,2.67,3,6.33,3.33]
      |  },{
      |    "type":"pie",
      |    "center":[100,80],
      |    "size":100,
      |    "data":[{
      |      "name":"Jane",
      |      "y":13
      |    },{
      |      "name":"John",
      |      "y":23
      |    },{
      |      "name":"Joe",
      |      "y":19
      |    }],
      |    "dataLabels":{
      |      "enabled":true
      |    }
      |  }]
      |}
    """.stripMargin
}
