
## Line Basic

This is an example to implement demo [Line Basic](href)
 demostrated on highcharts web site


```scala

import com.rockiey.zeppelin.highcharts._

val title = new Title("Combination chart")

val subtitle = new Subtitle(
  """|Source: <a href="http://thebulletin.metapress.com/content/c4120650912x74k7/fulltext.pdf">
                thebulletin.metapress.com</a>
  """.stripMargin)

val xAxis = new Axis().categories("Apples", "Oranges", "Pears", "Bananas", "Plums")

// TODO labels items



val seriesJane = Series(3, 2, 1, 3, 4).name("Jane")("type", "column")
val seriesJohn = Series(2, 3, 5, 7, 6).name("John")("type", "column")
val seriesJoe = Series(4, 3, 3, 9, 0).name("Joe")("type", "column")

val seriesAverage = Series(3.0, 2.67, 3, 6.33, 3.33).name("Average")("type", "spline")
val seriesTotalConsumption = Series(
  Map("name" -> "Jane", "y" -> 13),
  Map("name" -> "John", "y" -> 23),
  Map("name" -> "Joe", "y" -> 19)).typ("pie").
  center(100, 80).
  size(100).
  dataLabels("enabled", true)


val chart = new Highcharts(seriesJane, seriesJohn, seriesJoe, seriesAverage,seriesTotalConsumption)
chart.
  title(title).
  xAxis(xAxis)

chart.render()


```
