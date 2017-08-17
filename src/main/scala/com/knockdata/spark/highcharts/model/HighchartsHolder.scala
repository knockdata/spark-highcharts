package com.knockdata.spark.highcharts.model

import com.knockdata.spark.highcharts.base.{BaseModel, BasePlotOptions, Margin}
import com.knockdata.spark.highcharts.{AppendOutputMode, CompleteOutputMode, CustomSinkProvider, Registry, SeriesHolder, ZeppelinContextHolder, id}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.zeppelin.spark.ZeppelinContext

import scala.collection.mutable

class HighchartsHolder(seriesHolder: SeriesHolder) {
  var outputMode: String = null

  var chartParagraphId: String = null

  var z : ZeppelinContext = null


  def setZ(z: ZeppelinContext): this.type = {
    this.z = z
    this
  }

  def setChartParagraphId(chartParagraphId: String): this.type = {
    this.chartParagraphId = chartParagraphId
    this
  }

  def setOutputMode(mode: String): this.type = {
    this.outputMode = mode
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

  private val optionsBuffer = mutable.Buffer[BaseModel]()

  def appendOptions(options: BaseModel) = {
    optionsBuffer += options
    this
  }

  def start: StreamingQuery = {
    val chartId = seriesHolder.chartId

    Registry.put(s"$chartId-seriesHolder", seriesHolder)
//    Registry.put(s"$chartId-z", zHolder)
    Registry.put(s"$chartId-options", optionsBuffer)

    val writeStream = seriesHolder.dataFrame.writeStream
      .format(classOf[CustomSinkProvider].getCanonicalName)
      .option("chartId", chartId)
      .option("chartParagraphId", chartParagraphId)

    outputMode match {
      case "complete" =>
        Registry.put (s"$chartId-outputMode", new CompleteOutputMode())
        writeStream.outputMode("complete").start()
      case "append" =>
        Registry.put (s"$chartId-outputMode", new AppendOutputMode(200))
        writeStream.outputMode("append").start()
      case _ =>
        throw new Exception("outputMode must be either append or complete")
    }
  }
}
