package com.knockdata.spark.highcharts

import java.util.concurrent.ConcurrentHashMap

object Registry {
  val registry = new ConcurrentHashMap[String, Object]()

  def put(key: String, value: Object): Object = registry.put(key, value)

  def get(key: String): Object = registry.get(key)
}