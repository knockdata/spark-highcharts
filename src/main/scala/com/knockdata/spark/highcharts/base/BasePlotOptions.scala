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

package com.knockdata.spark.highcharts.base

abstract class BasePlotOptions extends BaseModel {

  def animationLimit(value: Int): this.type = {
    append("animationLimit", value)
  }

  def cursor(value: String): this.type = {
    append("cursor", value)
  }

  def enableMouseTracking(value: Boolean): this.type = {
    append("enableMouseTracking", value)
  }

  def events(values: (String, String)*): this.type = {
    append("events", values.toMap.map {
      case (k, v) =>
        k -> placeholdCode(v)
    })
  }

  def getExtremesFromAll(value: Boolean): this.type = {
    append("getExtremesFromAll", value)
  }

  def keys(value: String*): this.type = {
    append("keys", value)
  }


  // the value only have one item which is events
  def point(values: (String, Map[String, Any])*): this.type = {
    val events = values.collect {
      case ("events", v) => v
    }.head

    append("point", "events",
      events.map {
        case (k, v: String) => k -> placeholdCode(v)
      }
    )
  }

  /**
    * it is point.events, since only events in points
    * so just using one function without embedded structure
    */
  def pointEvents(values: (String, String)*): this.type = {
    append("point", "events", values.toMap.map {
      case (k, v) => k -> placeholdCode(v)
    }
    )
  }

  def selected(value: Boolean): this.type = {
    append("selected", value)
  }

  def stickyTracking(value: Boolean): this.type = {
    append("stickyTracking", value)
  }

  def tooltip(values: (String, Any)*): this.type = {
    append("tooltip", values.toMap)
  }

  def visible(value: Boolean): this.type = {
    append("visible", value)
  }

  def zoneAxis(value: String): this.type = {
    append("zoneAxis", value)
  }

  def zones(values: Map[String, Any]*): this.type = {
    append("zones", values.toList)
  }
}
