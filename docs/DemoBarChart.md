# Bar Chart Demo

Based on [Bar Basic Demo](http://www.highcharts.com/demo/bar-basic)

## Histogram

Based on [plot-histograms-in-highcharts](http://stackoverflow.com/questions/18042165/plot-histograms-in-highcharts)

an line chart with

* x axis data from column $"age"
* y axis number of record for age
* data point order by age


```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._

highcharts(bank
  .series("x" -> "age", "y" -> count("*"))
  .orderBy(col("age")))
  .chart(Chart.column)
  .plotOptions(PlotOptions.column.groupPadding(0).pointPadding(0).borderWidth(0))
  .plot()
```

## Stacked Column

Based on [Stacked Column](http://www.highcharts.com/demo/column-stacked)

Column are stacked, each stack is one series which is person

* x axis is index of fruit types. it does not specified by in data series
* y from $"consumption"


```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._

import sqlContext.implicits._

val male = Seq(-2.2, -2.2, -2.3, -2.5, -2.7, -3.1, -3.2,
  -3.0, -3.2, -4.3, -4.4, -3.6, -3.1, -2.4,
  -2.5, -2.3, -1.2, -0.6, -0.2, -0.0, -0.0).map(v => ("Male", v))
val female = Seq(2.1, 2.0, 2.2, 2.4, 2.6, 3.0, 3.1, 2.9,
  3.1, 4.1, 4.3, 3.6, 3.4, 2.6, 2.9, 2.9,
  1.8, 1.2, 0.6, 0.1, 0.0).map(v => ("Female", v))

val categories = List("0-4", "5-9", "10-14", "15-19",
"20-24", "25-29", "30-34", "35-39", "40-44",
"45-49", "50-54", "55-59", "60-64", "65-69",
"70-74", "75-79", "80-84", "85-89", "90-94",
"95-99", "100 + ")

val dataFrame = (male ++ female).toDF("gender", "population")

highcharts(dataFrame
  .seriesCol("gender")
  .series("y" -> "population"))
  .chart(Chart.bar)
  .xAxis(XAxis("").categories(categories))
  .xAxis(XAxis("").categories(categories).opposite(true).linkedTo(0))
  .plotOptions(PlotOptions.series.stacking("normal"))
  .plot()
```
