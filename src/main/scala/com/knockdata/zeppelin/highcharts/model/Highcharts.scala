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

package com.knockdata.zeppelin.highcharts.model

import com.knockdata.zeppelin.highcharts.base._
import com.knockdata.zeppelin.highcharts._

class Highcharts(series: Series*) extends BaseModel with Margin with PublicApply {
  override def fieldName: String = "highcharts"

  private var _drilldown: Option[Drilldown] = None

  def drilldown(drilldown: Drilldown): this.type = {
    _drilldown = Some(drilldown)
    this
  }

  def plot(): Unit = {
    val data = replaced

    val jq = "$"

    val chartId = id
    val code =
      s"""|%angular
          |
          |<div id="highcharts_$chartId" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
          |
          |<script type="text/javascript">
          |$jq(function () {
          |var data = $data
          |
          |$jq("#highcharts_$chartId").highcharts(data)
          |});
          |</script>""".stripMargin

    println(code)
  }

  def options(opts: BaseModel*): this.type = {
    options(opts.toList)
  }

  def options(opts: List[BaseModel]): this.type = {
    for (opt <- opts) {
      codes ++= opt.codes
    }

    val (plotOptions, otherOptions) = opts.partition(_.isInstanceOf[BasePlotOptions])
    for (option <- otherOptions) {
      append(option.fieldName, option)
    }
    for (plotOption <- plotOptions) {
      append("plotOptions", plotOption.fieldName, plotOption.result)
    }
    this
  }

  override def preProcessResult(): Unit = {
    append("series", series.toList)
    _drilldown.foreach(value => append("drilldown", value))

    super.preProcessResult
  }
}

