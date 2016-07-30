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

object Init {

  val allHighchartUrls = List(
    "http://code.highcharts.com/highcharts.js",
    "http://code.highcharts.com/highcharts-more.js",
    "http://code.highcharts.com/highcharts-3d.js",
    "http://code.highcharts.com/adapters/standalone-framework.js",
    "http://code.highcharts.com/modules/annotations.js",
    "http://code.highcharts.com/modules/boost.js",
    "http://code.highcharts.com/modules/broken-axis.js",
    "http://code.highcharts.com/modules/canvas-tools.js",
    "http://code.highcharts.com/modules/data.js",
    "http://code.highcharts.com/modules/exporting.js",
    "http://code.highcharts.com/modules/drilldown.js",
    "http://code.highcharts.com/modules/funnel.js",
    "http://code.highcharts.com/modules/heatmap.js",
    "http://code.highcharts.com/modules/no-data-to-display.js",
    "http://code.highcharts.com/modules/offline-exporting.js",
    "http://code.highcharts.com/modules/solid-gauge.js",
    "http://code.highcharts.com/modules/treemap.js",
    "http://code.highcharts.com/maps/modules/map.js",
    "http://code.highcharts.com/mapdata/countries/us/us-all.js")

  val highchartName2Url = allHighchartUrls.map {
    case url =>
      val name = url.stripSuffix(".js").split("/").last
      name -> url
  }.toMap


  def init(highcharts: String*): Unit = {

    val highchartUrls =
      if (highcharts.isEmpty)
        allHighchartUrls
      else {
        highcharts.map(name => highchartName2Url(name))
      }

    val loaders = highchartUrls.map {
      case url =>
        s"""
          |$$.getScript("$url")
          |  .done(function( script, textStatus ) {
          |    console.log( "load $url " + textStatus );
          |  })
          |  .fail(function(jqxhr, settings, exception ) {
          |     console.log("load $url " + exception);
          |  });
          | """.stripMargin
    }
    val template =
      s"""
        |%angular
        |<script type="text/javascript">
        |
        |$$(function () {
        |
        |${loaders.mkString("\n")}
        |
        |});
        |
        |</script>
      """.stripMargin

    println(template)
  }
}
