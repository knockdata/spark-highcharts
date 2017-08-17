package com.knockdata.spark.highcharts

import java.util.UUID

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming._
import org.apache.zeppelin.interpreter.InterpreterContext
import org.apache.zeppelin.spark.ZeppelinContext

object StreamingChart {
  def apply(z: ZeppelinContext): Unit = {
    println("%angular \n" + z.get(InterpreterContext.get().getParagraphId))
  }
}

class StreamingChart(dataFrame: DataFrame,
                     chartId: String,
                     nextParagraphId: String,
                     outputMode: String = null) extends StreamingQuery {
  //  HolderRegistry.put(seriesHolder.chartId, seriesHolder)
  var query: StreamingQuery = null

  val s = dataFrame.writeStream
    .format(classOf[CustomSinkProvider].getCanonicalName)
    .option("chartId", chartId)
    .option("nextParagraphId", nextParagraphId)

  val stream = if (outputMode == null) s else s.outputMode(outputMode)

  def start: this.type = {
    query = stream.start()
    this
  }

  def stop: Unit = {
    query.stop
    //    HolderRegistry.remove(seriesHolder.chartId)
  }

  override def name: String = query.name

  override def awaitTermination(): Unit = query.awaitTermination()

  override def awaitTermination(timeoutMs: Long): Boolean = query.awaitTermination(timeoutMs)

  override def processAllAvailable(): Unit = query.processAllAvailable()

  override def isActive: Boolean = query.isActive

  override def explain(): Unit = query.explain

  override def explain(extended: Boolean): Unit = query.explain(extended)

  override def sparkSession: SparkSession = query.sparkSession

  override def exception: Option[StreamingQueryException] = query.exception

  override def id = query.id

  override def runId: UUID = query.runId

  override def lastProgress: StreamingQueryProgress = query.lastProgress

  override def recentProgress: Array[StreamingQueryProgress] = query.recentProgress

  override def status: StreamingQueryStatus = query.status
}

