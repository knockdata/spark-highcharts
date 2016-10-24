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

        val customOutputMode = outputMode.asInstanceOf[CustomOutputMode]
        val seriesHolder = customOutputMode.seriesHolder
        seriesHolder.dataFrame = data

        val result = seriesHolder.result
        val (normalSeriesList, drilldownSeriesList) = customOutputMode.result(result._1, result._2)

        val chart = new Highcharts(normalSeriesList, seriesHolder.chartId)
          .drilldown(drilldownSeriesList)

        customOutputMode.onFinish(chart.plotData)
      }
    }
  }
}
