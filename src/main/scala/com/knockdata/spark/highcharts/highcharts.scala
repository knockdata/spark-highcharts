/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.knockdata.spark.highcharts

import com.knockdata.spark.highcharts.model._
import org.apache.zeppelin.spark.ZeppelinContext

import scala.collection.mutable

object highcharts {
  private def nextParagraphId(z: ZeppelinContext): String = {
    val currentParagraphId = z.getInterpreterContext.getParagraphId
    val paragraphIds = z.listParagraphs.toArray
    val currentIndex = paragraphIds.indexOf(currentParagraphId)
    paragraphIds(currentIndex + 1).toString
  }

  def streamingChart(seriesHolder: SeriesHolder,
                     zHolder: ZeppelinContextHolder,
                     chartParagraphId: String, outputMode: String="append"): HighchartsHolder = {
    new HighchartsHolder(seriesHolder, zHolder, chartParagraphId, outputMode)

  }

  def apply(seriesHolder: SeriesHolder,
            z: ZeppelinContext,
            outputMode: String = null): HighchartsHolder = {
    val chartParagraph = nextParagraphId(z)
    streamingChart(seriesHolder, new ZeppelinContextHolder(z), chartParagraph, outputMode)
  }

  def apply(seriesHolder: SeriesHolder,
            z: ZeppelinContext,
            outputMode: String,
            chartParagraph: String): HighchartsHolder = {
    streamingChart(seriesHolder, new ZeppelinContextHolder(z), chartParagraph, outputMode)
  }



  def apply(seriesHolders: SeriesHolder*): Highcharts = {
    val normalSeriesBuffer = mutable.Buffer[Series]()
    val drilldownSeriesBuffer = mutable.Buffer[Series]()
      for (holder <- seriesHolders) {
          val (normalSeriesList, drilldownSeriesList) = holder.result

          normalSeriesBuffer ++= normalSeriesList
          drilldownSeriesBuffer ++= drilldownSeriesList
      }

      new Highcharts(normalSeriesBuffer.toList).drilldown(drilldownSeriesBuffer.toList)
  }
}
