package com.knockdata.zeppelin.highcharts.base

import com.knockdata.zeppelin.highcharts.model._

import net.liftweb.json.JsonAST._

import scala.language.implicitConversions

/**
  * Created by Rockie Yang on 2016/06/01.
  */
object JsonImplicits {
  implicit def stringToOptionString(s: String): Option[String] = Some(s)

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
      case v: Map[String @unchecked, _] =>
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

  //  def pairToJArray(vs: Tuple2): JArray = {
  //
  //  }

  def toJValue(value: Any): JValue = {
    value match {
      case v: List[_] =>
        listToJArray(v)

      case v: Map[String @unchecked, _] =>
        mapToJObject(v)
      case null =>
        JNull
      case v: Any =>
        valueToJValue(v)

    }
  }

  implicit def listToJArray(vs: List[Any]): JArray = {
    val ar = vs.map(
      value => value match {

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
        case v: Map[String @unchecked, _] =>
          mapToJObject(v)
        case v: Array[_] =>
          listToJArray(v.toList)
        case _ =>
          valueToJValue(value)
      }
    )

    JArray(ar)
  }

//
//  def jvalueToString(jvalue: JValue): String = {
//    pretty(render(jvalue))
//  }
}
