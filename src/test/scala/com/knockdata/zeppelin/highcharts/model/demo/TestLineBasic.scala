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
  * @see <a href="http://www.highcharts.com/demo/line-basic">Line Basic</a>
  */
class TestLineBasic extends AbstractTestCase{

  @Test
  def testLineBasic(): Unit = {
    val title = new Title("Monthly Average Temperature").x(-20)
    val subtitle = new Subtitle("Source: WorldClimate.com").x(-20)
    val xAxis = new XAxis("").categories("Jan", "Feb", "Mar", "Apr", "May",
      "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val yAxis = new YAxis("Temperature (°C)").plotLines(
      Map("value" -> 0,
        "width" -> 1,
        "color" -> "#808080")
    )
    val tooltip = new Tooltip().valueSuffix("°C")
    val legend = new Legend().
      layout("vertical").
      align("right").verticalAlign("middle").borderWidth(0)

    val seriesTokyo = Series(
      7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6).
      name("Tokyo")


    val seriesNewYork = Series(
      -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5).
      name("New York")

    val seriesBerlin = Series(
      -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0).
      name("Berlin")

    val seriesLondon = Series(
      3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8).
      name("London")

    val chart = new Highcharts(seriesTokyo, seriesNewYork, seriesBerlin, seriesLondon)
    chart.options(title,
      subtitle,
      xAxis,
      yAxis,
      tooltip,
      legend)

    chart.plot()

    assertEqualJson(expected, chart)
  }


  val expected =
    """
      |{
      |  "title":{
      |    "text":"Monthly Average Temperature",
      |    "x":-20
      |  },
      |  "subtitle":{
      |    "text":"Source: WorldClimate.com",
      |    "x":-20
      |  },
      |  "xAxis":{
      |    "categories":["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]
      |  },
      |  "yAxis":{
      |    "plotLines":[{
      |      "value":0,
      |      "width":1,
      |      "color":"#808080"
      |    }],
      |    "title":{
      |      "text":"Temperature (°C)"
      |    }
      |  },
      |  "tooltip":{
      |    "valueSuffix":"°C"
      |  },
      |  "legend":{
      |    "borderWidth":0,
      |    "align":"right",
      |    "layout":"vertical",
      |    "verticalAlign":"middle"
      |  },
      |  "series":[{
      |    "data":[7,6.9,9.5,14.5,18.2,21.5,25.2,26.5,23.3,18.3,13.9,9.6],
      |    "name":"Tokyo"
      |  },{
      |    "data":[-0.2,0.8,5.7,11.3,17,22,24.8,24.1,20.1,14.1,8.6,2.5],
      |    "name":"New York"
      |  },{
      |    "data":[-0.9,0.6,3.5,8.4,13.5,17,18.6,17.9,14.3,9,3.9,1],
      |    "name":"Berlin"
      |  },{
      |    "data":[3.9,4.2,5.7,8.5,11.9,15.2,17,16.6,14.2,10.3,6.6,4.8],
      |    "name":"London"
      |  }]
      |}
    """.stripMargin
}
