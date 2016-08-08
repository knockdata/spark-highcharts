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

highcharts(dataFrame
  .series("name" -> "JobTitle", "y" -> avg($"BasePay"))
  .orderBy(avg($"BasePay").desc)
  .take(10))
  .chart(Chart.bar)
  .plot()
```
