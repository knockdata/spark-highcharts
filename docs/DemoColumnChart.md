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

highcharts(bank)
  .chart(Chart.column)
  .series("x" -> "age", "y" -> count("*"))
  .orderBy(col("age"))
  .plotOptions(new plotOptions.Column().groupPadding(0).pointPadding(0).borderWidth(0))
  .plot()
```
