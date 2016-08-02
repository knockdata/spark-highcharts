# Select Data to Plot

## Import packages

The first thing to is import packages

	import com.knockdata.zeppelin.highcharts._
	import com.knockdata.zeppelin.highcharts.model._
	
## Select Data Source

### Already have a DataFrame

`bank` is a DataFrame in the following example. 

    highcharts(bank)
    
> NOTE: The DataFrame does not have to aggerated before plot. zeppelin-highcharts will aggegrate the data according to the configuration

### Have a table registered in sqlContext

	highcharts(sqlContext.table("bank"))
	
### Have a Dataset
	
DataFrame is just a alias from Dataset since Spark 1.6. So just use a Dataset as a DataFrame

`type DataFrame = Dataset[Row]`

Dataset has a function `toDF` which will convert a Dataset to DataFrame

## Select columns

Highcharts has Axis. e.g. Line chart has `x` axis and `y` axis.

DataFrame has columns. e.g. `age`, `balance`

Use function `series` to define how to get Highcharts axis value from the DataFrame.

	highcharts(bank)
	  .series("x" -> "age", "y" -> avg(col("balance")))

In the previous example.
   
|	Map 				        |	Highcharts axis	|	DataFrame Columns
| ------------------------------|-------------------|-------------------
| "x" -> "age"                  | x                 | age
| "y" -> avg(col("balance"))    | y                 | average balance

It will be aggregated before plot. It will be translated to.

    bank.groupBy("age").agg(avg(col("balance")))
    
Then get data from aggregated result and map to Highcharts axis.

## Order

Without explicit orderBy, the data could be out of order. Add orderBy function to keep the data in order.

	highcharts(bank)
	  .series("x" -> "age", "y" -> avg(col("balance")))
	  .orderBy(col("age")).plot()
	  
orderBy by default is using ascencding order.  Add desc to make the data in descending order.

	highcharts(bank)
	  .series("x" -> "age", "y" -> avg(col("balance")))
	  .orderBy(col("age").desc).plot()

## Series and Drilldown

`series` is used for top level chart. `drilldown` is used to define dive in level

## seriesCol

Multiple series will be plotted if seriesCol is provided. 

In the following example 3 series will be plotted. Since there are 3 distinct value for column `marital` which are `married`, `single` and `devorced`. 

	highcharts(bank)
	  .seriesCol("marital")
	  .series("x" -> "age", "y" -> avg(col("balance")))
	  .orderBy(col("age").desc).plot()
