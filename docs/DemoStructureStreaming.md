# Bar Chart Demo

Based on [Bar Basic Demo](http://www.highcharts.com/demo/bar-basic)


```scala

import com.knockdata.spark.highcharts._
import com.knockdata.spark.highcharts.model._
import sqlContext.implicits._


val input = MemoryStream[String]
val doubled = input.toDS().map(x => x + " " + x)


val inputData = List("hi", "holden", "bye", "pandas")
input.addData(inputData)


val query = doubled.writeStream
  .format("com.knockdata.spark.highcharts.demo.CustomSinkCollectorProvider")
  .start()


query.processAllAvailable()

input.addData(List("hello", "world"))
query.processAllAvailable()
query.awaitTermination(10000)
input.addData(List("from", "rockie"))
println(SparkEnv.spark)
```


```scala

import com.knockdata.spark.highcharts._
import com.knockdata.spark.highcharts.model._
import sqlContext.implicits._

val input = MemoryStream[String]
val doubled = input.toDS().map(x => x + " " + x)

input.addData(List("hi", "holden", "bye", "pandas"))
  input.addData(List("hi", "holden", "bye", "pandas"))
input.addData("hi")
val query = doubled.writeStream
  .format("com.knockdata.spark.highcharts.demo.CustomSinkCollectorProvider")
  .start()


input.addData(List("hello", "world"))
query.processAllAvailable()

println(SparkEnv.spark)
```


```scala

import com.knockdata.spark.highcharts._
import com.knockdata.spark.highcharts.model._
import sqlContext.implicits._


  val input = MemoryStream[String]
  val doubled = input.toDS().map(x => x + " " + x)
  val inputData = List("hi", "holden", "bye", "pandas")
  input.addData(inputData)

  val query = doubled.writeStream
.queryName("testCustomSinkBasic")
      .queryName("testCustomSinkBasic")
    .format("com.knockdata.spark.highcharts.demo.CustomSinkCollectorProvider")
    .start()

  query.processAllAvailable()

  println(SparkEnv.spark)

```
