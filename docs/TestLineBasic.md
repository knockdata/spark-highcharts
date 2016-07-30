
## Line Basic

This is an example to implement demo [Line Basic](href)
 demostrated on highcharts web site


```scala

import com.rockiey.zeppelin.highcharts._


val chart: Highcharts = (
  DataSet.dfBank,
  List("x" -> "age",
    "y" -> avg(col("balance")),
    "orderBy" -> col("age"))
  )

chart.render()

val chart: Highcharts = (
  DataSet.dfBank,
  List("x" -> "age",
    "y" -> avg(col("balance")),
    "orderBy" -> col("age").asc)
  )

chart.render()

val chart: Highcharts = (
  DataSet.dfBank,
  List("name" -> "age",
    "y" -> avg(col("balance")),
    "orderBy" -> col("age").desc
  ))

chart("xAxis", "type", "category").render()

```
