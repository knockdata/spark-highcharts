[![Build Status](https://travis-ci.org/knockdata/spark-highcharts.svg?branch=master)](https://travis-ci.org/knockdata/spark-highcharts)

# Spark Highcharts

Add Highcharts support to Apache Spark. Both in spark-shell and Zeppelin.

Make Spark DataFrame visualization simple, flexible, and beautiful.

## Used it in Apache Zeppelin

#### Run with docker

[Install docker](https://docs.docker.com/engine/installation/) if you have not.

    docker run -p 8080:8080 -d knockdata/zeppelin-highcharts

> the docker image zeppelin-highcharts includes features

> * highcharts
> * pyspark, include
> * git, git lfs, vim

0.6.0: Spark 1.6, Scala 2.10, Zeppelin 0.6.0
0.6.1: Spark 2.0, Scala 2.11, Zeppelin 0.6.1

If you wanna run on your existing zeppelin, follow [Use In Zeppelin](https://github.com/knockdata/spark-highcharts/blob/master/docs/UseInZeppelin.md).

#### Open browser

	http://localhost:8080

#### Load the bank DataFrame

* Open and execute `Zeppelin Tutorial NoteBook`

#### Plot

Add a sub praragraph in `Zeppelin Tutorial NoteBook`

Paste the following code and execute it.

	import com.knockdata.spark.highcharts._
	import com.knockdata.spark.highcharts.model._

	highcharts(bank
	  .series("x" -> "age", "y" -> avg(col("balance")))
	  .orderBy(col("age"))).plot()


You will get the following graph

![zeppelin-spark-basic-line-chart](https://raw.githubusercontent.com/knockdata/spark-highcharts/master/docs/zeppelin-spark-basic-line-chart.png)

### Working with Spark 2.0

The default version before 0.6 support Spark 1.6. In order to use Spark 2.0, compile with

    mvn clean package -Pspark-2.0 -DskipTests


The `artifact` in the spark interpreter need use jar file for spark-highcharts. And the lift-json need use version for scala 2.11.

`/home/rockiey/git/spark-highcharts/target/spark-highcharts-0.6.4.jar`

`net.liftweb:lift-json_2.11:2.6.3`

[Select Data to Plot](https://github.com/knockdata/spark-highcharts/blob/master/docs/SelectDataToPlot.md)

[Chart Options](https://github.com/knockdata/spark-highcharts/blob/master/docs/ChartOptions.md)

[Demos](https://github.com/knockdata/spark-highcharts/blob/master/docs/demos.md)

[Type Mapping between Scala and Highcharts](https://github.com/knockdata/spark-highcharts/blob/master/docs/TypeMapping.md)

[Scaladocs](https://knockdata.github.io/spark-highcharts/docs/scaladocs)

## Use it in spark-shell

download spark-highcharts and lift-json jar file.

add following line to spark-defaults.conf

    spark.jars spark-highcharts-0.6.4.jar,lift-json_2.10-2.6.3.jar

## License

spark-highchart use Apache 2.0 License

However, the Highcharts JavaScript library that is included in this package is not free for commercial use.
Please contact [Highcharts](https://shop.highsoft.com/) for license related issues.
