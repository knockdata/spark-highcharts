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

package com.knockdata.zeppelin.highcharts.base

import com.knockdata.zeppelin.highcharts.model._

import net.liftweb.json.JsonAST._

import scala.language.implicitConversions

object JsonConversion {
  def stringToOptionString(s: String): Option[String] = Some(s)

  def isInt(value: Double): Boolean = {
    value == Math.floor(value) && !java.lang.Double.isInfinite(value)
  }

  def valueToJValue(value: Any): JValue = {
    value match {
      case v: Int =>
        JInt(v)
      case v: Long =>
        JInt(v)
      case v: Double =>
        if (isInt(v))
          JInt(v.toInt)
        else
          JDouble(v)

      case v: String =>
        JString(v)
      case v: Boolean =>
        JBool(v)
      case null =>
        JNull
      case v: JValue =>
        v
      case v: Code =>
        throw new Exception("it should not arrive here, BaseModel need be enhanced to support more fieldValue")
      case v: List[_] =>
        listToJArray(v)
      case v: Map[String@unchecked, _] =>
        mapToJObject(v)
      case x: Any =>
        JString(x.toString)
    }
  }

  def mapToJObject(vs: Map[String, Any]): JObject = {
    val vss = vs.map { case (name, value) =>
      JField(name, valueToJValue(value))
    }
    JObject(vss.toList)
  }

  def toJValue(value: Any): JValue = {
    value match {
      case v: List[_] =>
        listToJArray(v)

      case v: Map[String@unchecked, _] =>
        mapToJObject(v)
      case null =>
        JNull
      case v: Any =>
        valueToJValue(v)
    }
  }

  def listToJArray(vs: List[Any]): JArray = {
    val ar = vs.map {
      case v: JValue =>
        v
      case v: BaseModel =>
        v.result
      case v: (_, _) =>
        listToJArray(v.productIterator.toList)
      case v: List[_] =>
        if (v.size == 1)
          toJValue(v.head)
        else
          listToJArray(v)
      case v: Seq[_] =>
        if (v.size == 1)
          toJValue(v.head)
        else
          listToJArray(v.toList)
      case v: Map[String@unchecked, _] =>
        mapToJObject(v)
      case v: Array[_] =>
        listToJArray(v.toList)
      case value =>
        valueToJValue(value)
    }

    JArray(ar)
  }
}
