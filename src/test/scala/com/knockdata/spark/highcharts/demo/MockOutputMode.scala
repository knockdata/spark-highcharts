package com.knockdata.spark.highcharts.demo

import com.knockdata.spark.highcharts._
import org.apache.spark.sql.streaming.OutputMode

class MockOutputMode(seriesHolder: SeriesHolder, maxPoints: Int = 200)
  extends OutputMode {

  def onFinish(res: String): Unit = {
    result = res
  }
  var result: String = null
}

