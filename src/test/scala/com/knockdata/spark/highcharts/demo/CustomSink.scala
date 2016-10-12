package com.knockdata.spark.highcharts.demo

import org.apache.spark.sql.{SQLContext, _}
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.sources.StreamSinkProvider
import org.apache.spark.sql.streaming.OutputMode

    case class CustomSink(func: DataFrame => Unit)
      extends Sink {

      override def addBatch(batchId: Long, data: DataFrame): Unit = {
        func(data)
      }
    }

    class CustomSinkProvider extends StreamSinkProvider {
      def func(df: DataFrame) {
        df.show(5)
      }

      def createSink(
                      sqlContext: SQLContext,
                      parameters: Map[String, String],
                      partitionColumns: Seq[String],
                      outputMode: OutputMode): CustomSink = {
        new CustomSink(func)
      }
    }
