package com.knockdata.spark.highcharts.demo

import com.knockdata.spark.highcharts._

class MockAppendOutputMode(seriesHolder: SeriesHolder, maxPoints: Int = 200)
  extends AppendOutputMode(seriesHolder, maxPoints) {
  override def onFinish(res: String): Unit = {
    result = res
  }
  var result: String = null
}

class MockCompleteOutputMode(seriesHolder: SeriesHolder)
  extends CompleteOutputMode(seriesHolder) {
  override def onFinish(res: String): Unit = {
    result = res
  }
  var result: String = null
}
