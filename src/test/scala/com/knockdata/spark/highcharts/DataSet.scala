/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.knockdata.spark.highcharts

import java.io.File

object DataSet {
  import SparkEnv.sqlContext.implicits._

  val dfI = Seq(
    Tuple1(1),
    Tuple1(2),
    Tuple1(3)).toDF("I")

  val dfII = Seq(
    (1,2),
    (3,4),
    (5,6)).toDF("I1", "I2")

  val dfCI = Seq(
    ("category1",1),
    ("category2",2),
    ("category3",3)).toDF("C", "I")

  val dfCII= Seq(
    ("category1",1,2),
    ("category2",3,4),
    ("category3",5,6)).toDF("C", "I1", "I2")

  val dfSI= Seq(
    ("serial1", 11),("serial1", 13),("serial1", 15),
    ("serial2", 21),("serial2", 23),("serial2", 25),
    ("serial3", 31),("serial3", 33),("serial3", 35)
  ).toDF("S", "I")


  /**
    * Demo 3 columns DataFrame
    * Series, x, y
    *
    * The result should have 3 series
    */
  val dfSII= Seq(
    ("serial1", 1,12),("serial1", 2,14),("serial1", 4,16),
    ("serial2", 1,22),("serial2", 2,6),("serial2", 4,26),
    ("serial3", 1,32),("serial3", 2,34),("serial3", 3,10)
  ).toDF("S", "I1", "I2")


  val dfSABV = Seq(
    ("s1", "a1", "b1", 111),
    ("s1", "a1", "b2", 112),
    ("s1", "a1", "b3", 113),
    ("s1", "a2", "b1", 121),
    ("s1", "a2", "b2", 122),
    ("s1", "a2", "b3", 123),
    ("s1", "a3", "b1", 131),
    ("s1", "a3", "b1", 132),
    ("s1", "a3", "b1", 133),
    ("s2", "a1", "b1", 211),
    ("s2", "a1", "b2", 212),
    ("s2", "a1", "b3", 213),
    ("s2", "a2", "b1", 221),
    ("s2", "a2", "b2", 222),
    ("s2", "a2", "b3", 223),
    ("s2", "a3", "b1", 231),
    ("s2", "a3", "b1", 232),
    ("s2", "a3", "b1", 233),
    ("s3", "a1", "b1", 311),
    ("s3", "a1", "b2", 312),
    ("s3", "a1", "b3", 313),
    ("s3", "a2", "b1", 321),
    ("s3", "a2", "b2", 322),
    ("s3", "a2", "b3", 323),
    ("s3", "a3", "b1", 331),
    ("s3", "a3", "b1", 332),
    ("s3", "a3", "b1", 333)
  ).toDF("S", "A", "B", "V")

  val dfSCII= Seq(
    ("serial1", "category1",11,12),("serial1", "category2",13,14),("serial1", "category3",15,16),
    ("serial2", "category1",21,22),/*serial2 category2 missing*/("serial2", "category3",25,26),
    ("serial3", "category1",31,32),("serial3", "category2",33,34),("serial3", "category3",35,36)
  ).toDF("S", "C", "I1", "I2")


  case class Bank(age: Integer, job: String, marital: String, education: String, balance: Integer)

  lazy val dfBank = {
    if (new File("cache/bank.parquet").exists()) {
      SparkEnv.sqlContext.read.parquet("cache/bank.parquet").cache()
    }
    else {
      val bank = SparkEnv.sc.parallelize(
        scala.io.Source.fromFile("src/test/resources/bankSample.csv").getLines().toSeq)
        .map{s => s.split(";")}.filter(s => s(0) != "\"age\"").map(
        s => Bank(s(0).toInt,
          s(1).replaceAll("\"", ""),
          s(2).replaceAll("\"", ""),
          s(3).replaceAll("\"", ""),
          s(5).replaceAll("\"", "").toInt
        )
      ).toDF().cache()
      bank.write.parquet("cache/bank.parquet")
      bank
    }
  }

  case class SnowDepth(year: Int, time: Long, depth: Double)
  lazy val dfSnowDepth = SparkEnv.sc.parallelize(
      scala.io.Source.fromFile("src/test/resources/snowdepth.csv").getLines().toSeq)
      .map{s => s.split(",")}.filter(s => s(0) != "year").map(
        s => SnowDepth(s(0).toInt,
          s(1).toLong,
          s(2).toDouble
        )
      ).toDF.cache()

  val dfBrowserShare = Seq(
    ("Microsoft Internet Explorer", "v11.0", 24.13),
    ("Microsoft Internet Explorer", "v10.0", 5.33),
    ("Microsoft Internet Explorer", "v9.0", 8.11),
    ("Microsoft Internet Explorer", "v8.0", 17.2),
    ("Microsoft Internet Explorer", "v7.0", 0.5),
    ("Microsoft Internet Explorer", "v6.0", 1.06),
    ("Chrome", "v43.0", 1.45),
    ("Chrome", "v42.0", 3.68),
    ("Chrome", "v41.0", 4.32),
    ("Chrome", "v40.0", 5.0),
    ("Chrome", "v39.0", 2.96),
    ("Chrome", "v38.0", 0.6),
    ("Chrome", "v36.0", 2.53),
    ("Chrome", "v37.0", 0.38),
    ("Chrome", "v35.0", 0.85),
    ("Chrome", "v34.0", 0.14),
    ("Chrome", "v33.0", 0.19),
    ("Chrome", "v32.0", 0.55),
    ("Chrome", "v31.0", 1.24),
    ("Chrome", "v30.0", 0.14),
    ("Firefox", "v38.0", 1.02),
    ("Firefox", "v37.0", 2.31),
    ("Firefox", "v36.0", 2.32),
    ("Firefox", "v35.0", 2.76),
    ("Firefox", "v34.0", 1.27),
    ("Firefox", "v33.0", 0.22),
    ("Firefox", "v32.0", 0.15),
    ("Firefox", "v31.0", 0.22),
    ("Safari", "v8.0", 2.56),
    ("Safari", "v7.1", 0.77),
    ("Safari", "v7.0", 0.26),
    ("Safari", "v6.2", 0.17),
    ("Safari", "v6.1", 0.29),
    ("Safari", "v5.1", 0.42),
    ("Safari", "v5.0", 0.3),
    ("Opera", "v29", 0.16),
    ("Opera", "v28", 0.24),
    ("Opera", "v27", 0.17),
    ("Opera", "v12.x", 0.34)
  ).toDF("browser", "version", "share").cache()

  def dfWorldPopulationByRegion = Seq(
    ("Africa", 1800, 107),
    ("America", 1800, 31),
    ("Asia", 1800, 635),
    ("Europe", 1800, 203),
    ("Oceania", 1800, 2),
    ("Africa", 1900, 133),
    ("America", 1900, 156),
    ("Asia", 1900, 947),
    ("Europe", 1900, 408),
    ("Oceania", 1900, 6),
    ("Africa", 2012, 1052),
    ("America", 2012, 954),
    ("Asia", 2012, 4250),
    ("Europe", 2012, 740),
    ("Oceania", 2012, 38)
  ).toDF("country", "year", "population").cache()


}
