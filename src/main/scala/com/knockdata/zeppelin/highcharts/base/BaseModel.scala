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

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._

import net.liftweb.json.JsonAST.{JField, JObject, render}
import net.liftweb.json._

import scala.collection.mutable

trait IModel {
  protected def append(field: JField): this.type
  protected def append(fieldName: String, fieldValue: Any): this.type
  protected def append(fieldName: String, subFieldName: String, fieldValue: Any): this.type
  def fieldName: String
}

abstract class BaseModel extends IModel {

  def expect(actual: Any, options: Any*): Unit = {
    if (options.toSet.contains(actual)) {
      // some thing might be wrong
      val code =
        s"""
          |console.log("$actual not in expected ${options.mkString(",")}");
        """.stripMargin
      println(code)
    }
  }

  protected def preProcessResult(): Unit = appendSubItems()

  protected def postProcessResult(jobj: JObject): JObject = jobj

  /**
    * append all sub items to the result.
    *
    * The sub items has to be in objects.
    * That's it can only be done before all data is ready
    */
  protected def appendSubItems(): Unit = {
    for ((name, values) <- subFields) {
      val fields = values.map {
        case (subname, value) =>
          JField(subname, JsonConversion.toJValue(value))
      }
      val jobj = JObject(fields)
      append(name, jobj)
    }
  }

  /**
    * Lazy evaluate the result.
    *
    * 1. preProcessResult
    * 2. create JObject
    * 3. postProcessResult
    */
  lazy val result: JObject = {
    preProcessResult()

    postProcessResult(JObject(fields.result))

  }


  lazy val json: String = {
    pretty(render(result))
  }

  def compactJson: String = {
    compact(render(result))
  }

  /**
    * store or fields which will be put into the result JObject
    */
  val fields = mutable.ListBuffer[JField]()

  /**
    * used to reported problem if the same fields add twice
    */
  val fieldsNames = mutable.Set[String]()


  /**
    * each Model, two levels of fields are supported
    *
    * [fieldName, List[(subFieldName, subFieldValue)]
    */
  val subFields = mutable.LinkedHashMap[String, List[(String, Any)]]()

  /** Store code temporary
    * since code is not valid JSON
    * so put Stakeholder first, after everything created as String
    * just before substitute it with real code
    *
    * s"--$$fieldName-$$codeMD5--" -> Real JavaScript code
    */
  val codes = mutable.Map[String, String]()


  // using DefaultFormats, while it can be override
  // https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#extracting-values
  implicit val formats = net.liftweb.json.DefaultFormats

  /**
    * append a JField, it's the main entry
    */
  override protected def append(field: JField) = {
    if (fieldsNames.contains(field.name)) {
      println("something is strange")
    }
    fields += field
    fieldsNames += field.name
    this
  }

  protected def append(name: String, value: Any) = {
    // only append when value is NOT empty
    value match {
      case "" =>
      case Nil =>
      case model: BaseModel =>
        append(name, model.result)
      case _ =>
        append(JField(name, JsonConversion.toJValue(value)))
    }

    this
  }

  protected def append(fieldName: String, subFieldName: String, subFieldValue: Any) = {
    val prev = subFields.getOrElse(fieldName, Nil)

    // only append when value is note empty
    subFieldValue match {
      case "" =>
      case Nil =>
      case code: Code =>
        val placeholder = placeholdCode(code)
        subFields(fieldName) = (subFieldName, placeholder) :: prev
      case (t1, code: Code) =>
        val placeholder = placeholdCode(code)
        subFields(fieldName) = (subFieldName, (t1, placeholder)) :: prev
      case v: List[Any] =>
        val vs = v.map {
          case (k, code: Code) =>
            val placeholder = placeholdCode(code)
            (k, placeholder)
          case code: Code =>
            val placeholder = placeholdCode(code)
            placeholder
          case (k, v: Any) =>
            (k, v)
          case s: Any =>
            s
        }
        subFields(fieldName) = (subFieldName, vs) :: prev
      case _ =>
        subFields(fieldName) = (subFieldName, subFieldValue) :: prev
    }

    this
  }

  protected def placeholdCode(code: String): String = {
    val codeMD5 = md5(code)
    val placeholder = s"--code-$codeMD5--"

    codes += placeholder -> code

    placeholder
  }

  protected def placeholdCode(code: Code): String = {
    placeholdCode(code.code)
  }

  lazy val replaced: String = {
    val beforeFunctionReplace: String = json

    (beforeFunctionReplace /: codes) {
      (result, item) => result.replaceAllLiterally(s""""${item._1}"""", item._2)
    }
  }
}
