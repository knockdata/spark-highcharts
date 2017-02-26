# Area Chart Demo

Based on [Area Basic Demo](http://www.highcharts.com/demo/area-basic)

## Heatmap

Based on [Area Basic Demo](http://www.highcharts.com/demo/area-basic)


an line chart with

* x axis data from column $"age"
* y axis number of record for age
* data point order by age


```scala

import com.knockdata.spark.highcharts._
import com.knockdata.spark.highcharts.model._
import sqlContext.implicits._


val dataFrame = airQuality.toDF("dayOfWeek", "hour", "particle")

val d3Green = "#2ca02c"
val d3Orange = "#ff7f0e"
val d3LightGreen = "#98df8a"
val d3Red = "#d62728"

val scale = Seq(d3LightGreen, d3Green, d3Orange, d3Red)
val stops = Seq(Seq(0, scale(0)), Seq(0.3, scale(1)), Seq(0.5, scale(2)), Seq(1, scale(3)))
val chart = highcharts(dataFrame
  .series("x" -> "dayOfWeek", "y" -> "hour", "value" -> "particle").orderBy($"dayOfWeek", $"hour")
).chart(Chart.heatmap)("colorAxis", "stops", stops)

chart.plot()


```
