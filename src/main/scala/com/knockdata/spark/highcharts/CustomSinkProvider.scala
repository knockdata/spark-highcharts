package com.knockdata.spark.highcharts

import com.knockdata.spark.highcharts.model.Highcharts
import org.apache.spark.sql._
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.sources.StreamSinkProvider
import org.apache.spark.sql.streaming.OutputMode

class CustomSinkProvider extends StreamSinkProvider {
  def createSink(
                  sqlContext: SQLContext,
                  parameters: Map[String, String],
                  partitionColumns: Seq[String],
                  outputMode: OutputMode): Sink = {
    new Sink {
      override def addBatch(batchId: Long, data: DataFrame): Unit = {

        val chartId = parameters("chartId")
        val chartParagraphId = parameters("chartParagraphId")

        println(s"batchId: $batchId, chartId: $chartId, chartParagraphId: $chartParagraphId")
//        data.show(3)

        val z = Registry.get(s"$chartId-z").asInstanceOf[ZeppelinContextHolder]
        val seriesHolder = Registry.get(s"$chartId-seriesHolder").asInstanceOf[SeriesHolder]
        val outputMode = Registry.get(s"$chartId-outputMode").asInstanceOf[CustomOutputMode]

        seriesHolder.dataFrame = data

        val result = seriesHolder.result
        val (normalSeriesList, drilldownSeriesList) = outputMode.result(result._1, result._2)

        val chart = new Highcharts(normalSeriesList, seriesHolder.chartId)
          .drilldown(drilldownSeriesList)

        val plotData = chart.plotData
        val escaped = plotData.replace("%angular", "")
        println(s" put $chartParagraphId $escaped")
        z.put(chartParagraphId, plotData)
        println(s"run $chartParagraphId")
        z.run(chartParagraphId)
      }
    }
  }
}
