package com.knockdata.spark.highcharts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming._
import org.apache.zeppelin.interpreter.InterpreterContext
import org.apache.zeppelin.spark.ZeppelinContext

object StreamingChart{
  def apply(z: ZeppelinContext): Unit = {
    println(z.get(InterpreterContext.get().getParagraphId))
  }
}

class StreamingChart(outputMode: CustomOutputMode) extends StreamingQuery{
//  HolderRegistry.put(seriesHolder.chartId, seriesHolder)
  var query: StreamingQuery = null

    val stream = outputMode.seriesHolder.dataFrame.writeStream
      .format(classOf[CustomSinkProvider].getCanonicalName)
      .outputMode(outputMode)

  def start: Unit = {
    query = stream.start()
  }

  def stop: Unit = {
    query.stop
//    HolderRegistry.remove(seriesHolder.chartId)
  }

  override def name: String = query.name

  override def awaitTermination(): Unit = query.awaitTermination()

  override def awaitTermination(timeoutMs: Long): Boolean = query.awaitTermination(timeoutMs)

  override def processAllAvailable(): Unit = query.processAllAvailable()

  override def sinkStatus: SinkStatus = query.sinkStatus

  override def isActive: Boolean = query.isActive

  override def explain(): Unit = query.explain

  override def explain(extended: Boolean): Unit = query.explain(extended)

  override def sparkSession: SparkSession = query.sparkSession

  override def sourceStatuses: Array[SourceStatus] = query.sourceStatuses

  override def exception: Option[StreamingQueryException] = query.exception

  override def id: Long = query.id
}

