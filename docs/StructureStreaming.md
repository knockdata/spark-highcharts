# Stucture Streaming

For structure streaming plot,there are 3 steps.

* __Define Chart__ in a seperate paragraph. It is used to define how the chart want to be aggregated and plotted. The Spark Structure Streaming will be started.
* __Chart Paragraph__ in a dedicated paragraph to host the chart. It will be updated each time when new streaming data come.
* __Stop Plot__ in a seperate paragraph. Stop the spark streaming when no new plot wanted.


## Define Chart

For a `structuredDataFrame` after aggregation, with the following code in one Zeppelin paragraph. The [OutputMode](http://spark.apache.org/docs/latest/structured-streaming-programming-guide.html#output-modes) can be either `append` or `complete` depends how the structureDataFrame is aggregated.

### Plot the chart in the next paragraph in zeppelin

`spark-highcharts` will assume the __Plot Paragraph__ is the next paragraph if no plot __Plot Paragraph__ provided.

    import com.knockdata.spark.highcharts._
    import com.knockdata.spark.highcharts.model._

    val query = highcharts(
      structuredDataFrame.seriesCol("country")
        .series("x" -> "year", "y" -> "stockpile")
        .orderBy(col("year")), z, "append")

### Plot the chart in the specified paragraph in zeppelin

__Plot Paragraph__ can be provided to specify which Zeppelin paragraph want to be used as plot paragraph. Paragraph id is generated automatically when a new paragraph added. We can get the paragraph id by click ![Option](option.png) on the top right corner of the paragraph.

    import com.knockdata.spark.highcharts._
    import com.knockdata.spark.highcharts.model._

    val chartParagraph = "20161221-015648_847855252"
    val query = highcharts(
      structuredDataFrame.seriesCol("country")
        .series("x" -> "year", "y" -> "stockpile")
        .orderBy(col("year")), z, "append")

## Plot Paragraph

Only following line need be added in the plot paragraph.


    StreamingChart(z)

And the following code in the next paragraph. The chart in this paragraph will be updated when there are new data coming to the structureDataFrame.

## Stop Plot

Run following code to stop plot chart .

    query.stop()

## An Example

Here is the example generate structureDataFrame.

### Sample Data

    import sqlContext.implicits._
    val input = MemoryStream[NuclearStockpile]

    spark.conf.set("spark.sql.streaming.checkpointLocation","/usr/zeppelin/checkpoint")

    case class NuclearStockpile(country: String, stockpile: Int, year: Int)

    val USA = Seq(0, 0, 0, 0, 0, 6, 11, 32, 110, 235, 369, 640,
      1005, 1436, 2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,
      27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,
      26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,
      24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344, 23586,
      22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
      10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104).
        zip(1940 to 2006).map(p => NuclearStockpile("USA", p._1, p._2))

    val USSR = Seq(0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      5, 25, 50, 120, 150, 200, 426, 660, 869, 1060, 1605, 2471, 3322,
      4238, 5221, 6129, 7089, 8339, 9399, 10538, 11643, 13092, 14478,
      15915, 17385, 19055, 21205, 23044, 25393, 27935, 30062, 32049,
      33952, 35804, 37431, 39197, 45000, 43000, 41000, 39000, 37000,
      35000, 33000, 31000, 29000, 27000, 25000, 24000, 23000, 22000,
      21000, 20000, 19000, 18000, 18000, 17000, 16000).
        zip(1940 to 2006).map(p => NuclearStockpile("USSR/Russia", p._1, p._2))

    input.addData(USA.take(30) ++ USSR.take(30))
    val structureDataFrame = input.toDF

And the following code can be simulate to update the chart. The chart will be updated when the following code run.

    input.addData(USA.drop(30) ++ USSR.drop(30))

> NOTE: The example using Zeppelin 0.6.2 and Spark 2.0
