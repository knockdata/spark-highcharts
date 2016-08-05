# Pie Chart Demo

Based on [Pie Chart Demo](http://www.highcharts.com/demo/pie-basic)

## Histogram

Based on [plot-histograms-in-highcharts](http://stackoverflow.com/questions/18042165/plot-histograms-in-highcharts)

an line chart with

* x axis data from column $"age"
* y axis number of record for age
* data point order by age


```scala

import com.knockdata.zeppelin.highcharts._
import com.knockdata.zeppelin.highcharts.model._

import org.apache.spark.sql.functions._
import sqlContext.implicits._

val dataFrame = Seq(
  (95.0, 95.0, 13.8, "BE", "Belgium" ),
  (86.5, 102.9, 14.7, "DE", "Germany" ),
  (80.8, 91.5, 15.8, "FI", "Finland" ),
  (80.4, 102.5, 12.0, "NL", "Netherlands" ),
  (80.3, 86.1, 11.8, "SE", "Sweden" ),
  (78.4, 70.1, 16.6, "ES", "Spain" ),
  (74.2, 68.5, 14.5, "FR", "France" ),
  (73.5, 83.1, 10.0, "NO", "Norway" ),
  (71.0, 93.2, 24.7, "UK", "United Kingdom" ),
  (69.2, 57.6, 10.4, "IT", "Italy" ),
  (68.6, 20.0, 16.0, "RU", "Russia" ),
  (65.5, 126.4, 35.3, "US", "United States" ),
  (65.4, 50.8, 28.5, "HU", "Hungary" ),
  (63.4, 51.8, 15.4, "PT", "Portugal" ),
  (64.0, 82.9, 31.3, "NZ", "New Zealand")
).toDF("fat intake", "sugar intake", "obesity", "country code", "country")


highcharts(dataFrame)
  .chart(Chart.bubble)
  .series(
    "x" -> "fat intake",
    "y" -> "sugar intake",
    "z" -> "obesity",
    "name" -> "country code",
    "country" -> "country")
  .plotOptions(PlotOptions.bubble.dataLabels("enabled" -> true, "format" -> "{point.name}"))
  .xAxis(XAxis("fat intake daily"))
  .yAxis(YAxis("sugar intake daily")
      .plotLine(
        "dashStyle" -> "dot",
        "color" -> "black",
        "value" -> 50,
        "width" -> 3,
        "label" -> Map("text" -> "Safe sugar intake 50g/day", "align" -> "right"),
        "zIndex" -> 3)
  )
  .tooltip(new Tooltip().pointFormat("{point.country}"))
  .plot()


```
