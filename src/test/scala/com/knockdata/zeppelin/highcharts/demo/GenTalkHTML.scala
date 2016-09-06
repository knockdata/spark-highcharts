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

package com.knockdata.zeppelin.highcharts.demo

import java.io.{File, FileWriter}

import org.junit.Test

import scala.collection.mutable.ListBuffer

class GenTalkHTML {
  val jq = "$"
  def title(next: String) = {

      s"""
        |<head>
        |    <link rel="stylesheet" href="highlight/styles/github.css">
        |    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        |    <link rel="stylesheet" href="css/github-markdown.css">
        |    <style>
        |    .markdown-body {
        |        box-sizing: border-box;
        |        min-width: 200px;
        |        max-width: 980px;
        |        margin: 0 auto;
        |        padding: 45px;
        |    }
        |    </style>
        |    <script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
        |    <script src="https://code.highcharts.com/highcharts.js"></script>
        |    <script src="highlight/highlight.pack.js"></script>
        |    <script>hljs.initHighlightingOnLoad();</script>
        |</head>
        |<body>
        |<div class="container">
        |    <article class="markdown-body">
        |        <h1>Spark Visualization</h1>
        |        </article>
        |</div>
        |<script>

        | $jq(document).on("keydown", function(e) {
        |            var code = e.which;
        |            if (code == 37) { // left
        |            }
        |            else if (code == 38) { // up
        |            }
        |            else if (code == 39) { // right
        |               window.location.href = '$next.html';
        |            } else if (code == 40) { // down
        |
        |            }
        |        });
        |</script>
        |</body>
      """.stripMargin
  }

  val camel = "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])"
  def camel2Readable(s: String): String = {
    s.replaceAll(camel, " ")
  }

  def thanks(previous: String): String = {
      s"""
        |<head>
        |    <link rel="stylesheet" href="highlight/styles/github.css">
        |    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        |    <link rel="stylesheet" href="css/github-markdown.css">
        |    <style>
        |    .markdown-body {
        |        box-sizing: border-box;
        |        min-width: 200px;
        |        max-width: 980px;
        |        margin: 0 auto;
        |        padding: 45px;
        |    }
        |    </style>
        |    <script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
        |    <script src="https://code.highcharts.com/highcharts.js"></script>
        |    <script src="https://code.highcharts.com/highcharts-more.js"></script>
        |    <script src="highlight/highlight.pack.js"></script>
        |    <script>hljs.initHighlightingOnLoad();</script>
        |</head>
        |<body>
        |<div class="container">
        |    <article class="markdown-body">
        |        <h1>Thanks</h1>
        |        </article>
        |</div>
        |<script>
        | $jq(document).on("keydown", function(e) {
        |            var code = e.which;
        |            if (code == 37) { // left
        |             window.location.href = '$previous.html';
        |            }
        |            else if (code == 38) { // up
        |            }
        |            else if (code == 39) { // right
        |            } else if (code == 40) { // down
        |
        |            }
        |        });
        |</script>
        |</body>
      """.stripMargin
  }
  def genTalkHTML(code: String, data: String, name: String, previous: String, next: String): String = {

    val header = camel2Readable(name.drop("demo".length))
    val html =
      s"""
        |<head>
        |    <link rel="stylesheet" href="highlight/styles/github.css">
        |    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        |    <link rel="stylesheet" href="css/github-markdown.css">
        |    <style>
        |    .markdown-body {
        |        box-sizing: border-box;
        |        min-width: 200px;
        |        max-width: 980px;
        |        margin: 0 auto;
        |        padding: 45px;
        |    }
        |    <!-- BODY {background:none transparent;}-->
        |    </style>
        |    <script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
        |    <script src="https://code.highcharts.com/highcharts.js"></script>
        |    <script src="https://code.highcharts.com/modules/drilldown.js"></script>
        |    <script src="highlight/highlight.pack.js"></script>
        |    <script>hljs.initHighlightingOnLoad();</script>
        |</head>
        |<body>
        |<div class="container">
        |    <article class="markdown-body">
        |        <h1>$header</h1>
        |<pre><code class="scala hljs">$code</code></pre>
        |
        |<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        |<script>
        | $jq(function () {
        |   var data = $data
        |   $jq('#container').highcharts(data);
        | });
        |
        | $jq(document).on("keydown", function(e) {
        |            var code = e.which;
        |            if (code == 37) { // left
        |             window.location.href = '$previous.html';
        |            }
        |            else if (code == 38) { // up
        |            }
        |            else if (code == 39) { // right
        |               window.location.href = '$next.html';
        |            } else if (code == 40) { // down
        |
        |            }
        |        });
        |</script>
        | </article>
        |</div>
        |</body>
      """.stripMargin
    html
  }

  @Test
  def genMarkdown(): Unit = {


    try {
      val titleWriter = new FileWriter(s"talks/title.html")
      val thanksWriter = new FileWriter(s"talks/thanks.html")
      titleWriter.write(title(toc(1)))
      thanksWriter.write(thanks(toc(toc.length - 2)))
      titleWriter.close()
      thanksWriter.close()
    }
    catch {
      case ex =>
        println(ex)
    }

    for (filename <- getDemoFiles) {
      println(s"generate mark down for $filename")

      traverseDemo(s"$path/$filename")
    }
  }

  val path = "src/test/scala/com/knockdata/zeppelin/highcharts/demo/"


  def writeHTML(codes: List[String], data: List[String], name: String): Unit = {
    val writer = new FileWriter(s"talks/$name.html")
    try {
      val index = toc.indexOf(name)
      val previous = if (index <= 0) 0 else index - 1
      val next = if (index == toc.length - 1) toc.length - 1 else index + 1
      val html = genTalkHTML(
        codes.mkString("\n"),
        data.mkString("\n"),
        name,
        toc(previous),
        toc(next))
      writer.write(html)
    }
    finally {
      writer.close()
    }
  }

  val toc = Array(
    "title",
    "demoBarHistogram",				"demoLineBasic",				"demoSFSalaries",
  "demoBasicArea",				"demoLineBasicAsc",				"demoSplineInverted",
  "demoBasicLine",				"demoLineBasicDesc",				"demoSplineWithPlotBands",
  "demoDonut",					"demoLineBasicMultipleSeries",	"demoStackedColumn",
  "demoDrilldown2Level",			"demoLineBasicMultipleSeriesWithoutOption",	"demoTimeDataWithIrregularIntervals",
  "demoDrilldownBasic",				"demoLineWithDataLabels",
  "demoHistogram",				"demoLineZoomable",
  
    "thanks")

  def getDemoFiles = {

    new File(path).list().filter(filename => filename.startsWith("Demo") && filename.endsWith(".scala"))
  }


  object Type extends Enumeration {
    val Code, Other = Value
  }

  def traverseDemo(filename: String) = {
    val source = scala.io.Source.fromFile(filename)
    var inScope = false
    var lineType = Type.Other

    val rootDocPrefix: String = "// "
    val rootDocPrefixLen = rootDocPrefix.length
    val docPrefix: String = "    //"
    val docPrefixLen = rootDocPrefix.length


    val buffer = new ListBuffer[String]()
    def append(line: String): Unit = {
      buffer += line
      println(line)
    }

    try {
      val lines = source.getLines().toArray
      var name = ""
      for (line <- lines) {
//        println(line)

        val trimLine = line.trim()

//        if (trimLine.startsWith("//"))
//          append(trimLine.drop(2).trim)

        if (line.startsWith("  def demo")) {

          name = line.drop("  def ".length).takeWhile(_ != '(')

          inScope = true

        }
        else if (line.startsWith("  }")) {
          inScope = false
          val data = scala.io.Source.fromFile(s"target/$name.json").getLines()
          writeHTML(buffer.toList, data.toList, name)
          buffer.clear()
        }
        else if (line.contains("new PrintWriter")) {
          // doing nothing
        }
        else {
          if (inScope) {
            append(line.drop(4))
          }
        }
      }
    }
    finally {
      source.close()
    }
    buffer.result
  }
}
