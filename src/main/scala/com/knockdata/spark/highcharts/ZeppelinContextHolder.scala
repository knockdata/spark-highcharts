package com.knockdata.spark.highcharts

import org.apache.zeppelin.spark.ZeppelinContext
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._

class ZeppelinContextHolder(z: ZeppelinContext) {
  def put(name: String, value: AnyRef) = z.put(name, value)
  def run(paragraphId: String) = {
    Future(z.run(paragraphId))
  }
}
