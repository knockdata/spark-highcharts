package com.knockdata.spark.highcharts

import org.apache.spark.{SparkContext, SparkConf}

import org.apache.commons.io.IOUtils
import java.net.URL
import java.nio.charset.Charset



/**
  * Created by Rockie Yang on 2016/06/16.
  */
object SparkEnv {


  lazy val sc = {
        val master = "local"
//    val master = "spark://Rockies-MacBook-Pro.local:7077"
    val conf = new SparkConf()
      .setAppName("Simple Application")
      .setMaster(master)
//      .setJars(Seq("/Users/rockieyang/git/zeppelin-highcharts/target/zeppelin-highcharts-0.1-SNAPSHOT.jar"))
    new SparkContext(conf)


  }


  val sqlContext= new org.apache.spark.sql.SQLContext(sc)



//  def createDF(seq: Seq[Any]) = {
//    sqlContext.createDataFrame(seq)
//  }

}
