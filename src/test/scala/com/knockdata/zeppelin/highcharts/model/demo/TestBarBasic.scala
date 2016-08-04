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
  * @see <a href="http://www.highcharts.com/demo/bar-basic">Bar Basic</a>
  */
class TestBarBasic extends AbstractTestCase{

  @Test
  def testAreaBasic: Unit = {
    val title = new Title("Historic World Population by Region")

    val subtitle = new Subtitle(
      """|Source: <a href='https://en.wikipedia.org/wiki/World_population'>Wikipedia.org</a>
      """.stripMargin)

    val xAxis = new XAxis(null).categories("Africa","America","Asia","Europe","Oceania")

    val yAxis = new YAxis("Population (millions)").min(0)(
      "title", "align", "high")(
      "labels", "overflow", "justify")

    val tooltip = new Tooltip().valueSuffix(" millions")


    val legend = new Legend()(
      "layout", "vertical")(
      "align", "right")(
      "verticalAlign", "top")(
      "x", -40)(
      "y", 80)(
      "floating", true)(
      "borderWidth", 1)(
      "shadow",true)
//    legend.appendFunction("backgroundColor",
//      "((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF')")


    val barPlotOptions = PlotOptions.bar("dataLabels", "enabled", true)

    val series1800 = Series(107, 31, 635, 203, 2).name("Year 1800")
    val series1900 = Series(133, 156, 947, 408, 6).name("Year 1900")
    val series2012 = Series(1052, 954, 4250, 740, 38).name("Year 2012")


    val chart = new Highcharts(series1800, series1900, series2012)
    chart.options(Chart.bar,
      title,
      subtitle,
      xAxis,
      yAxis,
      tooltip,
      barPlotOptions,
      legend)("credits", "enabled", false)


    chart.plot()

    assertEqualJson(expected, chart)
  }


  val expected =
    """
      |{
      |  "chart":{
      |    "type":"bar"
      |  },
      |  "title":{
      |    "text":"Historic World Population by Region"
      |  },
      |  "subtitle":{
      |    "text":"Source: <a href='https://en.wikipedia.org/wiki/World_population'>Wikipedia.org</a>\n      "
      |  },
      |  "xAxis":{
      |    "categories":["Africa","America","Asia","Europe","Oceania"],
      |    "title":{
      |      "text":null
      |    }
      |  },
      |  "yAxis":{
      |    "min":0,
      |    "title":{
      |      "text":"Population (millions)",
      |      "align":"high"
      |    },
      |    "labels":{
      |      "overflow":"justify"
      |    }
      |  },
      |  "tooltip":{
      |    "valueSuffix":" millions"
      |  },
      |  "plotOptions":{
      |    "bar":{
      |      "dataLabels":{
      |        "enabled":true
      |      }
      |    }
      |  },
      |  "legend":{
      |    "layout":"vertical",
      |    "align":"right",
      |    "verticalAlign":"top",
      |    "x":-40,
      |    "y":80,
      |    "floating":true,
      |    "borderWidth":1,
      |    "shadow":true,
      |  },
      |  "series":[{
      |    "name":"Year 1800",
      |    "data":[107,31,635,203,2]
      |  },{
      |    "name":"Year 1900",
      |    "data":[133,156,947,408,6]
      |  },{
      |    "name":"Year 2012",
      |    "data":[1052,954,4250,740,38]
      |  }],
      |  "credits":{
      |    "enabled":false
      |  }
      |}
    """.stripMargin
}
