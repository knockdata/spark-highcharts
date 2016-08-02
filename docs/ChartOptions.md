# Chart Option

Highcharts can be fine controlled with chart options. Check [Understanding Highcharts](http://www.highcharts.com/docs/chart-concepts/understanding-highcharts) article on Highcharts web site.

### Common Options
zeppelin-highcharts using following function to specify chart options. The interface are try to follow Highcharts API as much as possible.

	highcharts(dataFrame)
		.chart(Chart("column)[.some extra configuration])
		.credits(Credits("Highcharts.com")[.some extra configuration])
		.exporting(new Exporting()[.some extra configuration])
		.labels(new Label()[.some extra configuration])
		.legend(new Legend()[.some extra configuration])
		.navigation(new Navigation()[.some extra configuration])
		.noData(new NoData()[.some extra configuration])
		.pane(new Pane()[.some extra configuration])
		.subtitle(Subtitle("subtitle)[.some extra configuration])
		.title(Title("title")[.some extra configuration])
		.xAxis(XAxis("x")[.some extra configuration])
		.yAxis(YAxis("y")[.some extra configuration])
		

Companion object are provided if it is a default property, e.g. title `text` for `Title`. 

### plotOptions

Each charts has it's own plotOption class defined. The plotOptions classes are defined in package `com.knockdata.zeppelin.highchart.plotOptions`. Use cooresponding plotOption class to contol the chart.

	
### Series and Drilldown

For `Series` and `Drilldown`, check [Select Data to Plot](docs/SelectDataToPlot.md)

