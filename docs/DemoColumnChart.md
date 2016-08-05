# Column Chart Demo

Based on [Column Basic Demo](http://www.highcharts.com/demo/column-basic)

## Histogram

Based on [plot-histograms-in-highcharts](http://stackoverflow.com/questions/18042165/plot-histograms-in-highcharts)

an line chart with

* x axis data from column $"age"
* y axis number of record for age
* data point order by age


```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._

highcharts(
  bank
    .series("x" -> "age", "y" -> count("*"))
    .orderBy(col("age"))
  )
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

val john = Seq(5, 3, 4, 7, 2).map(v => ("John", v))
val jane = Seq(2, 2, 3, 2, 1).map(v => ("Jane", v))
val joe = Seq(3, 4, 4, 2, 5).map(v => ("Jeo", v))

val dataFrame = (john ++ jane ++ joe).toDF("name", "consumption")

highcharts(
  dataFrame
    .seriesCol("name")
    .series("y" -> "consumption"))
  .chart(Chart.column)
  .xAxis(XAxis("").categories("Apples", "Oranges", "Pears", "Grapes", "Bananas"))
  .plotOptions(PlotOptions.column.stacking("normal"))
  .plot()
```
