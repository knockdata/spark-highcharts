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

import org.junit.Test

import scala.collection.mutable

class ModelAnalyzer {
  val lines : List[String] = {
    // the file is from http://api.highcharts.com/highcharts/names
    val lines = scala.io.Source.fromFile("src/test/resources/highcharts.4.2.5.names.json").getLines().toList
    lines.tail.init.map {
      line =>
        line.replace('"', ' ').replace(',',' ').trim
    }
  }

  def combo2(values: List[String]): List[(String, String)] = {
    val buffer = mutable.Buffer[(String, String)]()
    def combination0(current: String, rest: List[String]): Unit = {
      rest.foreach(item => buffer += current -> item)
      rest match {
        case head :: Nil =>
          // no more to traverse
        case head :: tail =>
          combination0 (head, tail)
      }
    }

    combination0(values.head, values.tail)
    buffer.toList
  }

  val plotOptionsLines: List[String] = {
    lines.filter(_.startsWith("plotOptions-"))
      .map(_.drop("plotOptions-".length))
  }

  val chartTypes = {
    val allPlotOptions = plotOptionsLines.map {_.split('-').head}.distinct
//    allPlotOptions diff List("funnel", "gauge", "solidgauge", "pyramid", "pie",
//      "heatmap", "treemap",
//      "errorbar",
//    "boxplot", "polygon", "waterfall", "scatter", "bubble")
    allPlotOptions
  }


  val chartTypePlotOptions: List[(String, List[String])] = chartTypes.map {
    option =>
      val len = option.length + 1
      option -> plotOptionsLines.collect {
        case line if line.startsWith(option) => line.drop(len)
      }
  }

  val topTreePlotOptions = chartTypePlotOptions.map {
    case (k, options) =>
      k -> options.filter(o => !o.contains('-'))
  }

  val topLeafPlotOptions = chartTypePlotOptions.map {
    case (k, options) =>
      k -> options.filter(o => o.startsWith("-"))
  }

  val topPlotOptions = chartTypePlotOptions.map {
    case (k, options) =>
      k -> options.filter(o => o.startsWith("-") || !o.contains('-'))
  }



  val plotOptionsMap: Map[String, List[String]] = chartTypePlotOptions.toMap

  val topTreePlotOptionsMap: Map[String, List[String]] = topTreePlotOptions.toMap
  val topLeafPlotOptionsMap: Map[String, List[String]] = topLeafPlotOptions.toMap

  val topPlotOptionsMap: Map[String, List[String]] = topPlotOptions.toMap

  val commonOptions: List[String] = plotOptionsMap.values.reduce(_ intersect _)
  val commonTopPlotOptions: List[String] = topPlotOptionsMap.values.reduce(_ intersect _)

  val combos = combo2(chartTypes)

  val commonTopCombos = combos.map {
    case (a, b) =>
      val optionsA = topPlotOptionsMap(a)
      val optionsB = topPlotOptionsMap(b)

      (a, optionsA.size, b, optionsB.size) -> (optionsA intersect optionsB)
  }.sortBy(_._2.size)

  val commonTopCombosLeaf = combos.map {
    case (a, b) =>
      val optionsA = topLeafPlotOptionsMap(a)
      val optionsB = topLeafPlotOptionsMap(b)

      (a, optionsA.size, b, optionsB.size) -> (optionsA intersect optionsB)
  }.sortBy(_._2.size)


  @Test
  def testCommonTop(): Unit = {

    println(commonTopPlotOptions.mkString("\n"))
  }

  @Test
  def testCommonFunc(): Unit = {

    val commonFunc = commonTopPlotOptions.map(_.replace("-", "")).sorted.map{
      name =>
        s"""
          |  def $name(value: Any): this.type = {
          |    append("$name", value)
          |  }
        """.stripMargin
    }

    println(commonFunc.mkString)
  }


  @Test
  def testSpecialTop(): Unit = {
    for ((chartType, options) <- topPlotOptionsMap) {
      val specialOptions = options diff commonTopPlotOptions

        println(chartType)
        println(specialOptions.map(o => s"    $o").mkString("\n"))

    }

  }

  @Test
  def testSpecialTopFunctions(): Unit = {
    for ((chartType, options) <- topPlotOptionsMap) {
      val specialOptions = options diff commonTopPlotOptions

      val specialFunc = specialOptions.map(_.replace("-", "")).sorted.map{
        name =>
            s"""
              |  def $name(value: Any): this.type = {
              |    append("$name", value)
              |  }
            """.stripMargin
      }

      println(s"""   ------------------------- $chartType ---------------- """")

      println(s"""  def fieldName = "$chartType"""")
      println(specialFunc.mkString)

    }

  }

  @Test
  def testTopCombinations(): Unit = {

    println(commonTopCombos.mkString("\n"))
  }

  @Test
  def testTopLeafCombinations(): Unit = {
    val nums = commonTopCombosLeaf.map{case (k, v) => k -> v.size}

    println(nums.mkString("\n"))
//    println(commonTopLeaf.mkString("\n"))
  }

  @Test
  def testTopLeafCombosSpecial(): Unit = {
    val nums = commonTopCombosLeaf.map{case (k, v) => k -> v.size}

    println(nums.mkString("\n"))
    //    println(commonTopLeaf.mkString("\n"))
  }




  @Test
  def testOptions(): Unit = {







    println(s"common ${commonOptions.size}")
    println(commonOptions.map(o => s"  $o").mkString("\n"))

    for ((optionName, allOptions: List[String]) <- chartTypePlotOptions) {
      println(optionName)
      val extraOptions: List[String] = allOptions diff commonOptions
      println(extraOptions.map(o => s"    $o").mkString("\n"))
    }

//    val areaOptions = plotOptionsMap("area")
//    val areaRangeOption = plotOptionsMap("arearange")
//
//
//    val areaCommonOptions = areaOptions intersect areaRangeOption
//    println(s"areaCommonOptions ${areaCommonOptions.size}")
//    println(areaCommonOptions.map(o => s"  $o").mkString("\n"))
  }
}
