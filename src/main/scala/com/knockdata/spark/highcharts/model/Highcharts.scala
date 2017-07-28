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

import java.io.FileWriter

import com.knockdata.spark.highcharts.base._
import com.knockdata.spark.highcharts._
import org.apache.zeppelin.interpreter.InterpreterContext

import scala.collection.mutable

class Highcharts(seriesList: List[Series], val chartId: String = id) extends BaseModel with Margin with PublicApply {
  override def fieldName: String = "highcharts"

  def this(series: Series*) = this(series.toList)

  private var _drilldown: Option[Drilldown] = None
  private val optionsBuffer = mutable.Buffer[BaseModel]()

  def drilldown(drilldownSeriesList: List[Series]): this.type = {
    drilldownSeriesList match {
      case Nil =>
      case xs =>
        _drilldown = Some(new Drilldown(xs))
    }
    this
  }


  def drilldown(drilldown: Drilldown): this.type = {
    _drilldown = Some(drilldown)
    this
  }

  def chart(chart: Chart) =
    appendOptions(chart)

  def colorAxis(colorAxis: ColorAxis) =
    appendOptions(colorAxis)

  def credits(credits: Credits) =
    appendOptions(credits)

  def data(data: Data) =
    appendOptions(data)

  def exporting(exporting: Exporting) =
    appendOptions(exporting)

  def labels(labels: Labels) =
    appendOptions(labels)

  def legend(legend: Legend) =
    appendOptions(legend)

  def navigation(navigation: Navigation) =
    appendOptions(navigation)

  def noData(value: Any) {
    throw new Exception("does not support noData")
  }

  def pane(pane: Pane) =
    appendOptions(pane)

  def plotOptions(plotOptions: BasePlotOptions*) = {
    plotOptions.foreach(appendOptions)
    this
  }

  def subtitle(subtitle: Subtitle) =
    appendOptions(subtitle)

  def subtitle(subtitle: String) =
    appendOptions(Subtitle(subtitle))

  def title(title: Title) =
    appendOptions(title)

  def title(title: String) =
    appendOptions(Title(title))

  def tooltip(tooltip: Tooltip) =
    appendOptions(tooltip)

  def xAxis(xAxis: Axis) =
    appendOptions(xAxis)

  def yAxis(yAxis: Axis) =
    appendOptions(yAxis)

  def appendOptions(options: BaseModel) = {
    optionsBuffer += options
    this
  }

  def htmlContent(data: String): String = {
    val jq = "$"

      s"""|<html>
          |<head>
          |    <script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
          |    <script src="https://code.highcharts.com/highcharts.js"></script>
          |</head>
          |<body>
          |    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
          |<script>
          |    $jq(function () {
          |    var data = $data
          |    $jq('#container').highcharts(data);
          |});
          |</script>
          |</body>
          |</html>""".stripMargin

  }

  def html(path: String = ".", filename: String = id, open: Boolean = true): String = {
    val content = htmlContent(replaced)
    val file = new java.io.File(s"$path/highcharts_$filename.html")
//    tmpFile.deleteOnExit()
    println(file)
    val writer = new FileWriter(file)
    writer.write(content)
    writer.close()

    if (open)
      java.awt.Desktop.getDesktop().browse(file.toURI)

    file.getAbsolutePath
  }


  def plotData: String = {
    val jq = "$"
    val data = replaced

          s"""|<div id="highcharts_$chartId" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
              |
              |<script type="text/javascript">
              |$jq(function () {
              |var data = $data
              |
              |$jq("#highcharts_$chartId").highcharts(data)
              |});
              |</script>""".stripMargin
  }

  def compactPlotData: String = {
    val jq = "$"
    val data = compactReplaced

    s"""|<div id="highcharts_$chartId" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        |
              |<script type="text/javascript">
        |$jq(function () {
        |var data = $data
        |
              |$jq("#highcharts_$chartId").highcharts(data)
        |});
        |</script>""".stripMargin
  }


  def plot(): Unit = {
    println("%angular \n" + plotData)
  }

  def compactPlot(): Unit = {
    println("%angular \n" + compactPlotData)
  }


  def options(opts: BaseModel*): this.type = {
    options(opts.toList)
  }

  def options(opts: List[BaseModel]): this.type = {
    for (opt <- opts) {
      codes ++= opt.codes
    }

    val (plotOptions, otherOptions) = opts.partition(_.isInstanceOf[BasePlotOptions])

    val otherOptionsGroup: Map[String, List[BaseModel]] = otherOptions.groupBy(m => m.fieldName)

    for ((fieldName, options) <- otherOptionsGroup) {
      options match {
        case one :: Nil =>
          append(fieldName, one)
        // xAxis, yAxis can have multiple values
        case multiple =>
          append(fieldName, multiple)
      }
    }

    for (plotOption <- plotOptions) {
      append("plotOptions", plotOption.fieldName, plotOption.result)
    }
    this
  }

  override def preProcessResult(): Unit = {
    append("series", seriesList)
    for (s <- seriesList) {
      codes ++= s.codes
    }

    _drilldown.foreach(value => append("drilldown", value))
    options(optionsBuffer.toList: _*)

    super.preProcessResult()
  }
}

