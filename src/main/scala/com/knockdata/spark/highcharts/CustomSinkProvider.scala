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

        val z = customOutputMode.z

        println(customOutputMode.chartParagraphId)

        val seriesHolder = customOutputMode.seriesHolder

        seriesHolder.dataFrame = data
        val (normalSeriesList, drilldownSeriesList) = seriesHolder.result
        val chart = new Highcharts(normalSeriesList, seriesHolder.chartId)
          .drilldown(drilldownSeriesList)

        z.put(customOutputMode.chartParagraphId, chart.plotData)
        z.run(customOutputMode.chartParagraphId)

      }
    }
  }
}
