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

import plotoptions._

object PlotOptions {
  // get a new area plotOptions
  def area = new Area

  // get a new arearange plotOptions
  def arearange = new AreaRange

  // get a new areaspline plotOptions
  def areaspline = new AreaSpline

  // get a new areasplinerange plotOptions
  def areasplinerange = new AreaSplineRange

  // get a new bar plotOptions
  def bar = new Bar

  // get a new boxplot plotOptions
  def boxplot = new BoxPlot

  // get a new bubble plotOptions
  def bubble = new Bubble

  // get a new column plotOptions
  def column = new Column

  // get a new errorbar plotOptions
  def errorbar = new ErrorBar

  // get a new funnel plotOptions
  def funnel = new Funnel

  // get a new gauge plotOptions
  def gauge = new Gauge

  // get a new heatmap plotOptions
  def heatmap = new HeatMap

  // get a new line plotOptions
  def line = new Line

  // get a new pie plotOptions
  def pie = new Pie

  // get a new polygon plotOptions
  def polygon = new Polygon

  // get a new pyramid plotOptions
  def pyramid = new Pyramid

  // get a new scatter plotOptions
  def scatter = new Scatter

  // get a new series plotOptions, the series plotOption apply on all chart type
  def series = new Series

  // get a new solidgauge plotOptions
  def solidgauge = new SolidGauge

  // get a new spline plotOptions
  def spline = new Spline

  // get a new treemap plotOptions
  def treemap = new TreeMap

  // get a new waterfall plotOptions
  def waterfall = new Waterfall
}
