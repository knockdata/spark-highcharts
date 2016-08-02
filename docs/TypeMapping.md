# Type Mapping


| Highcharts Type               | Scala Type                                                | Example                       |
| --------------------- |:--------------------------------------------------------- |:----------------------------- |
| Boolean               | Boolean                                                   | chart.showAxes                |
| Number, if only int   | Int                                                       | chart.borderRadius            |
| Number, if arbitrary  | Double                                                    | xAxis.minorTickInterval       |
| String                | String                                                    | xAxis.chart.text              |
| Color                 | String                                                    | chart.borderColor             |
| Function              | String, 1*                | legend.labelFormatter         |
| CSSObject             | (String, Any)*                                            | chart.style                   |
| Array                 | Any*                                                      | pane.center                   |
| Object                | (String, Any)*                                            | yAxis.stackLabels.formatter   |
| Array of Array        | List[Any]*                                                | xAxis.units                   |
| Array of Object       | Map[String, Any]*                                         | yAxis.breaks                  |
| Object of Object      | (String, Map[String, Any])*                               | plotOptions.arearange.point   |

## Special type

> fillColor http://www.highcharts.com/demo/line-time-series

    fillColor: {
      linearGradient: {
      x1: 0,
      y1: 0,
      x2: 0,
      y2: 1
    },
      stops: [
      [0, Highcharts.getOptions().colors[0]],
      [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
      ]
    }
Using following code to map

	val options = new plotOptions.Area()
	      .fillColorLinearGradient("x1" -> 0, "y1" -> 0, "x2" -> 0, "y2" -> 1)
	      .fillColorStops((0, "Highcharts.getOptions().colors[0]"),
	          (1, "Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')"))
    