//package com.knockdata.spark.highcharts.demo
//
//import com.knockdata.spark.highcharts.model.Highcharts
//import org.apache.spark.sql.execution.streaming.Sink
//import org.apache.spark.sql.{SQLContext, _}
//import org.apache.spark.sql.sources.StreamSinkProvider
//import org.apache.spark.sql.streaming.OutputMode
//
//class MockSinkProvider extends StreamSinkProvider {
//  def createSink(
//                  sqlContext: SQLContext,
//                  parameters: Map[String, String],
//                  partitionColumns: Seq[String],
//                  outputMode: OutputMode): Sink = {
//    new Sink {
//      var resultFrame: DataFrame = null
//
//      override def addBatch(batchId: Long, data: DataFrame): Unit = {
//        val mockOutputMode = outputMode.asInstanceOf[MockOutputMode]
//        val seriesHolder = mockOutputMode.seriesHolder
//
//        if (resultFrame == null) {
//          resultFrame = data
//        } else {
//          resultFrame = resultFrame.union(data)
//        }
//
//        seriesHolder.dataFrame = resultFrame
//
//        val (normalSeriesList, drilldownSeriesList) = seriesHolder.result
//        val chart = new Highcharts(normalSeriesList, seriesHolder.chartId)
//          .drilldown(drilldownSeriesList)
//
//        mockOutputMode.put("plotData", chart.plotData)
//
//      }
//    }
//  }
//}
