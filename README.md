[![Build Status](https://travis-ci.org/knockdata/zeppelin-highcharts.svg?branch=master)](https://travis-ci.org/knockdata/zeppelin-highcharts)

# Zeppelin Highcharts

Highcharts support in Apache Zeppelin.

Make Spark DataFrame visualization simple, flexible, and beautiful.

## Get Started

#### Run with docker

[Install docker](https://docs.docker.com/engine/installation/) if you have not.

    docker run -p 8080:8080 -d knockdata/zeppelin-highcharts

> the docker image zeppelin-highcharts includes features

> * highcharts
> * pyspark, include
> * git, git lfs, vim

0.6.0: Spark 1.6, Scala 2.10, Zeppelin 0.6.0
0.6.1: Spark 2.0, Scala 2.11, Zeppelin 0.6.1

If you wanna run on your existing zeppelin, follow [Use In Zeppelin](https://github.com/knockdata/zeppelin-highcharts/blob/master/docs/UseInZeppelin.md).

#### Open browser

	http://localhost:8080

#### Load the bank DataFrame

* Open and execute `Zeppelin Tutorial NoteBook`

#### Plot

Add a sub praragraph in `Zeppelin Tutorial NoteBook`

Paste the following code and execute it

	%spark
	import com.knockdata.zeppelin.highcharts._
	import com.knockdata.zeppelin.highcharts.model._

	highcharts(bank
	  .series("x" -> "age", "y" -> avg(col("balance")))
	  .orderBy(col("age"))).plot()

You will get the following graph

![zeppelin-spark-basic-line-chart](https://raw.githubusercontent.com/knockdata/zeppelin-highcharts/master/docs/zeppelin-spark-basic-line-chart.png)

### Working with Spark 2.0

The default version before 0.6 support Spark 1.6. In order to use Spark 2.0, compile with

    mvn clean package -Pspark-2.0 -DskipTests


The `artifact` in the spark interpreter need use jar file for zeppelin-highcharts. And the lift-json need use version for scala 2.11.

`/home/rockiey/git/zeppelin-highcharts/target/zeppelin-highcharts-0.6.0-SNAPSHOT.jar`

`net.liftweb:lift-json_2.11:2.6.3`

[Select Data to Plot](https://github.com/knockdata/zeppelin-highcharts/blob/master/docs/SelectDataToPlot.md)

[Chart Options](https://github.com/knockdata/zeppelin-highcharts/blob/master/docs/ChartOptions.md)

[Demos](https://github.com/knockdata/zeppelin-highcharts/blob/master/docs/demos.md)

[Type Mapping between Scala and Highcharts](https://github.com/knockdata/zeppelin-highcharts/blob/master/docs/TypeMapping.md)

[Scaladocs](https://knockdata.github.io/zeppelin-highcharts/docs/scaladocs)

## License

zeppelin-highchart use Apache 2.0 License

However, the Highcharts JavaScript library that is included in this package is not free for commercial use. Please contact [Highcharts](https://shop.highsoft.com/) for license related issues.
