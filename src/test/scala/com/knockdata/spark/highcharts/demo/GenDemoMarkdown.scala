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

package com.knockdata.spark.highcharts.demo

import java.io.{File, FileWriter}

import org.junit.Test

import scala.collection.immutable
import scala.collection.mutable.ListBuffer

class GenDemoMarkdown {

  def genTalkHTML(data: String): String = {
    val jq = "$"
    val html =
      s"""
        |<head>
        |    <link rel="stylesheet" href="highlight/styles/github.css">
        |    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        |    <link rel="stylesheet" href="github-markdown.css">
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
        |        <h1>Basic Area</h1>
        |<pre><code class="scala hljs">
        |import com.knockdata.spark.highcharts._
        |import com.knockdata.spark.highcharts.model._
        |</code></pre>
        |
        |<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        |<script>
        | $jq(function () {
        |   var data = $data
        |   $jq('#container').highcharts(data);
        | });
        |</script>
        |
        | </article>
        |</div>
        |</body>
      """.stripMargin
    html
  }

  @Test
  def genMarkdown(): Unit = {

    for (filename <- getDemoFiles) {
      println(s"generate mark down for $filename")

      writeMD(getInfo(s"$path/$filename"), filename.stripSuffix(".scala"))
    }
  }

  val path = "src/test/scala/com/knockdata/spark/highcharts/demo/"


  def writeMD(md: List[String], filename: String): Unit = {
    val writer = new FileWriter(s"docs/$filename.md")
    try {
      writer.write(md.mkString("\n"))
    }
    finally {
      writer.close()
    }
  }

  def getDemoFiles = {

    new File(path).list().filter(filename => filename.startsWith("Demo"))
  }


  object Type extends Enumeration {
    val Code, Other = Value
  }

  def getInfo(filename: String) = {
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
      for (line <- lines) {
//        println(line)

        val trimLine = line.trim()

        if (trimLine.startsWith("//"))
          append(trimLine.drop(2).trim)

        if (line.startsWith("  def demo")) {
          inScope = true
          append("")
          append("```scala")
          append("")
          append("import com.knockdata.spark.highcharts._")
          append("import com.knockdata.spark.highcharts.model._")
          append("import sqlContext.implicits._")
          append("")

        }
        else if (line.startsWith("  }")) {
          append("```")
          append("")
          inScope = false
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
