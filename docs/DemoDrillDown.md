# Drilldown Demo

Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)

## Drilldown Basic

Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)

A line chart with

* x axis data from column $"marital"
* y axis aggregated the average balance

Then it drilldown to

* x axis data from column $"job"
* y axis aggregated the average balance

```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._

highcharts(bank)
  .series("name" -> "marital",
    "y" -> avg(col("balance")))
  .drilldown("name" -> "job",
    "y" -> avg(col("balance")))
  .chart(Chart("column"))
  .plot()
```

## Drilldown 2 Levels

Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)

A line chart with

* x axis data from column $"marital"
* y axis aggregated the average balance

Then it drilldown to

* x axis data from column $"job"
* y axis aggregated the average balance

Then it drill down to

* x axis data from column $"education"
* y axis aggregated the max balance

with 3 levels, the output is pretty big
number of data point is
size(marital) + size(marital) * size(balance)
+ size(marital) * size(balance) + size(education)

```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._


highcharts(bank)
  .series("name" -> "marital",
    "y" -> avg(col("balance")))
  .drilldown("name" -> "job",
    "y" -> avg(col("balance")))
  .drilldown("name" -> "education",
    "y" -> max(col("balance")))
  .chart(Chart("column"))
  .plot()


```

## Drilldown Multiple Series Chart

Based on [Column With Drilldown](http://www.highcharts.com/demo/column-drilldown)

A line chart with

* multiple series from column $"marital"
* x axis data from column $"job"
* y axis aggregated the average balance

Then it drill down to

* x axis data from column $"education"
* y axis aggregated the max balance

series with one level drilldown, the output is pretty big
number of data point is
size(marital) + size(marital) * size(balance)
+ size(marital) * size(balance) + size(education)

```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._


highcharts(bank)
  .seriesCol("marital")
  .series("name" -> "job",
    "y" -> avg(col("balance")))
  .drilldown("name" -> "education",
    "y" -> avg(col("balance")))
  .plot()
```
