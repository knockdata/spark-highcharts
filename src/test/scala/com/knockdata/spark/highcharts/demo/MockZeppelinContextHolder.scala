package com.knockdata.spark.highcharts.demo

import com.knockdata.spark.highcharts.ZeppelinContextHolder

import scala.collection.mutable

class MockZeppelinContextHolder extends ZeppelinContextHolder(null){

  val values = mutable.HashMap[String, AnyRef]()

  override def put(name: String, value: AnyRef) = values.put(name, value)
  override def run(paragraphId: String): Unit = println(s"mock run $paragraphId")

}
