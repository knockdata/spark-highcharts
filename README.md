[![Build Status](https://travis-ci.org/knockdata/zeppelin-highcharts.svg?branch=master)](https://travis-ci.org/knockdata/zeppelin-highcharts)

# Zeppelin Highcharts

Highcharts support in Apache Zeppelin


### Add the binary to Zeppelin

> Goto Zeppelin -> `Interpreters`

> Scroll down to find `spark`

> Click `Edit`

![zeppelin-spark-interpreter-edit](docs/zeppelin-spark-interpreter-edit.png)

> Scroll down to `Dependencies`

> Edit the `artifact` with the correct jar file

`com.knockdata:zeppelin-highcharts:0.3`

> Click `Save`

![zeppelin-spark-interpreter-edit](docs/zeppelin-spark-interpreter-add-artifact.png)

### Load Highcharts Javascript

> **You need a valid license if you use Highcharts for commercial use**

> Please contact [Highcharts](https://shop.highsoft.com/) for license related issues.

Paste the following code to a `Zeppelin` Paragraph and execute it

	%angular
	<script type="text/javascript">

		$(function () {
			if (Highcharts == null) {
				$.getScript("http://code.highcharts.com/highcharts.js")
				  .done(function( script, textStatus ) {
				    console.log( "load http://code.highcharts.com/highcharts.js " + textStatus );
				  })
				  .fail(function(jqxhr, settings, exception ) {
				     console.log("load http://code.highcharts.com/highcharts.js " + exception);
				  });
			} else {
			    console.log("highcharts already loaded");
			}
		});
	</script>

### Load the bank DataFrame

Just need execute `Zeppelin Tutorial NoteBook`
(
### Create your first chart with following code

Paste the following code and execute it

	%spark
	import com.knockdata.zeppelin.highcharts._
	import com.knockdata.zeppelin.highcharts.model._

	highcharts(bank)
	  .series("x" -> "age", "y" -> avg(col("balance")))
	  .orderBy(col("age")).plot()

You will get the following graph

![zeppelin-spark-basic-line-chart](docs/zeppelin-spark-basic-line-chart.png)

[Select Data to Plot](docs/SelectDataToPlot.md)

[Chart Options](docs/ChartOptions.md)

[Demos](docs/demos.md)

[Type Mapping between Scala and Highcharts](docs/TypeMapping.md)

[Scaladocs](https://knockdata.github.io/zeppelin-highcharts/docs/scaladocs)
