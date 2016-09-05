# San Fransisco Salaries Demo

Dataset from [Kaggle](https://www.kaggle.com/kaggle/sf-salaries)

## SF salaries

Dataset from [Kaggle](https://www.kaggle.com/kaggle/sf-salaries)

take top 10 payment job

* x job title
* y avg base pay
* data point order by avg base pay descending


```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._
import sqlContext.implicits._



val file = "src/test/resources/SF-Salaries.csv"
val dataFrame = sqlContext.read
  .format("com.databricks.spark.csv")
  .option("header", "true") // Use first line of all files as header
  .option("inferSchema", "true") // Automatically infer data types
  .load(file)

val chart = highcharts(dataFrame
  .series("name" -> "JobTitle", "y" -> avg($"BasePay"))
  .orderBy(avg($"BasePay").desc)
  .take(10))
  .chart(Chart.bar)

chart.plot()

```
