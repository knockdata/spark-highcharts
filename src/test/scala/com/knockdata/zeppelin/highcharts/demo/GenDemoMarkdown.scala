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

import scala.collection.immutable
import scala.collection.mutable.ListBuffer

class GenDemoMarkdown {

  @Test
  def genMarkdown(): Unit = {

    for (filename <- getDemoFiles) {
      println(s"generate mark down for $filename")

      writeMD(getInfo(s"$path/$filename"), filename.stripSuffix(".scala"))
    }
  }

  val path = "src/test/scala/com/knockdata/zeppelin/highcharts/demo/"


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
          append("import com.knockdata.zeppelin.highcharts._")
          append("import com.knockdata.zeppelin.highcharts.model._")
          append("")

        }
        else if (line.startsWith("  }")) {
          append("```")
          append("")
          inScope = false
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
