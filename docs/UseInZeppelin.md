### Add the binary to Zeppelin

> Goto Zeppelin -> `Interpreters`

> Scroll down to find `spark`

> Click `Edit`

![zeppelin-spark-interpreter-edit](zeppelin-spark-interpreter-edit.png)

> Scroll down to `Dependencies`

> Edit the `artifact` with the correct jar file

`com.knockdata:spark-highcharts:0.6.1`

> Click `Save`

![zeppelin-spark-interpreter-edit](zeppelin-spark-interpreter-add-artifact.png)

### Compile Dependencies

This will load the following jars due to the current compile dependency tree:
* spark-highcharts-0.6.1.jar
* lift-json_2.11-2.6.3.jar
* paranamer-2.4.1.jar

### Load Highcharts Javascript

Paste the following code to a `Zeppelin` Paragraph and execute it

	%angular
	<script type="text/javascript">

		$(function () {
		    if (typeof Highcharts == "undefined") {
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

If wanna use drilldown function, paste the following code to `Zeppelin` Paragraph and execute it


	%angular
	<script type="text/javascript">

		$(function () {
				$.getScript("https://code.highcharts.com/modules/drilldown.js")
				  .done(function( script, textStatus ) {
				    console.log( "load https://code.highcharts.com/modules/drilldown.js " + textStatus );
				  })
				  .fail(function(jqxhr, settings, exception ) {
				     console.log("load https://code.highcharts.com/modules/drilldown.js " + exception);
				  });
		});
	</script>
	
## License

spark-highcharts use Apache 2.0 License

However, the Highcharts JavaScript library that is included in this package is not free for commercial use. Please contact [Highcharts](https://shop.highsoft.com/) for license related issues.
