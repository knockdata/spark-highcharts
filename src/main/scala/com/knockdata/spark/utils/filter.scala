package com.knockdata.spark.utils

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object filter {
  type PredicateC2C = org.apache.spark.sql.Column => org.apache.spark.sql.Column
  type PredicateS2C = String => org.apache.spark.sql.Column

//  implicit columnNamePredictConvertion()
//  def apply(df: DataFrame, colNames: String*)(fun: Predicate): DataFrame = {
//    val f =
//    colNames.foldLeft(df)((resultDF, colName) => resultDF.filter(fun(col(colName))))
//  }
//
//  def isNull(df: DataFrame, colNames: String*): DataFrame =
//    apply(df, colNames)(isnull(col()))
}
