package com.knockdata.spark.utils

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import scala.util.Try

object functions {

  val dNull: java.lang.Double = null
  val iNull: java.lang.Integer = null
  val bNull: java.lang.Boolean = null

  val s2Boolean = udf[Boolean, String](s => s.toBoolean)
  val s2Double = udf[Double, String](s => s.toDouble)
  val s2Int = udf[Integer, String](s => s.toInt)


  val s2NullableBoolean = udf[Boolean, String](s => if (s.isEmpty) bNull else s.toBoolean)
  val s2NullableDouble = udf[Double, String](s => if (s.isEmpty) dNull else s.toDouble)
  val s2NullableInt = udf[Integer, String](s => if (s.isEmpty) iNull else s.toInt)


  // the value type for "" might infer as Any. Noticed in Spark 1.6
  val s2SafeBoolean = udf[Boolean, String](s => Try(s.toString.toBoolean).getOrElse(bNull))
  val s2SafeDouble = udf[Double, String](s => Try(s.toString.toDouble).getOrElse(dNull))
  val s2SafeInt = udf[Int, String](s => Try(s.toString.toInt).getOrElse(iNull))




}
