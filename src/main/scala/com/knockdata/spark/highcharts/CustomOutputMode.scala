package com.knockdata.spark.highcharts

import com.knockdata.spark.highcharts.model.{Drilldown, Series}
import org.apache.spark.sql.streaming.OutputMode
import org.apache.zeppelin.spark.ZeppelinContext

import scala.collection.mutable

abstract class CustomOutputMode(val seriesHolder: SeriesHolder) extends OutputMode {
  val values = mutable.Map[String, String]()

  def put(key: String, value: String): Unit = values.put(key, value)

  def get(key: String): Option[String] = values.get(key)

  def apply(key: String): String = values(key)


  def result(normalSeries: List[Series],
             drilldownSeries: List[Series]): (List[Series], List[Series]) =
    (normalSeries, drilldownSeries)

  def onFinish(result: String)
}

abstract class AppendOutputMode(seriesHolder: SeriesHolder, maxPoints: Int)
  extends CustomOutputMode(seriesHolder) {

  var currentNormalSeries = mutable.Map[String, Series]()
  var currentDrilldownSeries = mutable.Map[String, Series]()

  def merge(previous: mutable.Map[String, Series],
            currentSeriesList: List[Series]): mutable.Map[String, Series] = {
    val current = mutable.Map[String, Series]()
    for (series <- currentSeriesList) {
      current += series.id -> series
    }

    // for the existing series, if there are more point need be added
    for ((key, series) <- previous) {
      if (current.contains(key)) {
        println("\nprevious")
        println(series.values.mkString("\n"))
        println("\ncurrent")
        println(current(key).values.mkString("\n"))
        current(key).vs = (series.values ::: current(key).values).takeRight(maxPoints)

        println("\nvs")
        println(current(key).vs.mkString("\n"))

      }
      else {
        current += key -> series
      }
    }
    current

  }

  override def result(normalSeries: List[Series],
                      drilldownSeries: List[Series]): (List[Series], List[Series]) = {
    currentNormalSeries = merge(currentNormalSeries, normalSeries)
    currentDrilldownSeries = merge(currentDrilldownSeries, drilldownSeries)

    (currentNormalSeries.values.toList, currentDrilldownSeries.values.toList)
  }
}

abstract class CompleteOutputMode(seriesHolder: SeriesHolder)
  extends CustomOutputMode(seriesHolder) {

}

class ZeppelinAppendOutputMode(seriesHolder: SeriesHolder,
                               z: ZeppelinContext,
                               chartParagraphId: String,
                               maxPoints: Int = 200)
  extends AppendOutputMode(seriesHolder, maxPoints) {
  override def onFinish(result: String): Unit = {
    z.put(chartParagraphId, result)
    z.run(chartParagraphId)
  }
}

class ZeppelinCompleteOutputMode(seriesHolder: SeriesHolder,
                                 z: ZeppelinContext,
                                 chartParagraphId: String,
                                 maxPoints: Int = 200)
  extends CompleteOutputMode(seriesHolder) {
  override def onFinish(result: String): Unit = {
    z.put(chartParagraphId, result)
    z.run(chartParagraphId)
  }
}

object AppendOutputMode {
  def apply(seriesHolder: SeriesHolder,
            z: ZeppelinContext,
            chartParagraphId: String): AppendOutputMode = {
    AppendOutputMode(seriesHolder, z, chartParagraphId)
  }
}

object CompleteOutputMode {
  def apply(seriesHolder: SeriesHolder,
            z: ZeppelinContext,
            chartParagraphId: String): AppendOutputMode = {
    CompleteOutputMode(seriesHolder, z, chartParagraphId)
  }
}

