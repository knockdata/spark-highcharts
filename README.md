[![Build Status](https://travis-ci.org/knockdata/zeppelin-highcharts.svg?branch=master)](https://travis-ci.org/knockdata/zeppelin-highcharts)

# Zeppelin Highcharts

Highcharts support in Apache Zeppelin

**You need a valid license if you use Highcharts for commercial use**

Please contact [Highcharts](https://shop.highsoft.com/) for license related issues.


## Get started

### Build the binary

    git clone https://github.com/knockdata/zeppelin-highcharts.git
    cd zeppelin-highcharts
    mvn clean package -DskipTests

### Add the binary to Zeppelin

> Goto Zeppelin -> `Interpreters`

> Scroll down to find `spark`

> Click `Edit`
 
![zeppelin-spark-interpreter-edit](docs/zeppelin-spark-interpreter-edit.png)

> Scroll down to `Dependencies`

> Edit the `artifact` with the correct jar file 

> Click `Save`

![zeppelin-spark-interpreter-edit](docs/zeppelin-spark-interpreter-add-jar.png)

### Load Highcharts

Paste the following code to a `Zeppelin` Paragraph and execute it 

	%angular
	<script type="text/javascript">
	
	$(function () {
	
	
	$.getScript("http://code.highcharts.com/highcharts.js")
	  .done(function( script, textStatus ) {
	    console.log( "load http://code.highcharts.com/highcharts.js " + textStatus );
	  })
	  .fail(function(jqxhr, settings, exception ) {
	     console.log("load http://code.highcharts.com/highcharts.js " + exception);
	  });
	 
	
	});
	
	</script>

### Load the bank DataFrame 

Just need execute `Zeppelin Tutorial NoteBook` 

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

[What Data to Render](docs/series.md)
