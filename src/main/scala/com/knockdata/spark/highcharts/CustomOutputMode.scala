package com.knockdata.spark.highcharts

import org.apache.spark.sql.streaming.OutputMode
import org.apache.zeppelin.spark.ZeppelinContext

case class CustomOutputMode(seriesHolder: SeriesHolder,
                            z: ZeppelinContext,
                            chartParagraphId: String) extends OutputMode{

}
