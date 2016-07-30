
## Bar Basic

This is an example to implement demo [Bar Basic](href)
 demostrated on highcharts web site


```scala

import com.rockiey.zeppelin.highcharts._

val title = new Title("Historic World Population by Region")

val subtitle = new Subtitle(
  """|Source: <a href='https://en.wikipedia.org/wiki/World_population'>Wikipedia.org</a>
  """.stripMargin)

val xAxis = new Axis(null).categories("Africa","America","Asia","Europe","Oceania")

val yAxis = new Axis("Population (millions)").min(0)(
  "title", "align", "high")(
  "labels", "overflow", "justify")

val tooltip = new Tooltip().valueSuffix(" millions")


val legend = new Legend()(
  "layout", "vertical")(
  "align", "right")(
  "verticalAlign", "top")(
  "x", -40)(
  "y", 80)(
  "floating", true)(
  "borderWidth", 1)(
  "shadow",true)
  legend.appendFunction("backgroundColor",
    "((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF')")


val barPlotOptions = new BarPlotOptions()("dataLabels", "enabled", true)

val series1800 = Series(107, 31, 635, 203, 2).name("Year 1800")
val series1900 = Series(133, 156, 947, 408, 6).name("Year 1900")
val series2012 = Series(1052, 954, 4250, 740, 38).name("Year 2012")


val chart = new Highcharts(series1800, series1900, series2012)
chart.
  chart(new Chart("bar")).
  title(title).
  subtitle(subtitle).
  xAxis(xAxis).
  yAxis(yAxis).
  tooltip(tooltip).
  plotOptions(barPlotOptions).
  legend(legend)(
    "credits", "enabled", false)


chart.render()


```
