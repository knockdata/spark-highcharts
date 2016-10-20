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

package com.knockdata.spark

import java.security.MessageDigest
import java.util.UUID

import org.apache.spark.sql.DataFrame

import scala.language.implicitConversions

package object highcharts {

  // generate md5 string from data
  private [highcharts] def md5(data: String) =
    MessageDigest.getInstance("MD5").digest(data.getBytes)
      .map("%02X".format(_)).mkString

  // generate id
  private [highcharts] def id = UUID.randomUUID.toString.replace('-', '_')

  // implicit converter from DataFrame to SeriesHolder
  implicit def dataFrame2SeriesHolder(dataFrame: DataFrame): SeriesHolder =
    new SeriesHolder(dataFrame)
//
//  implicit def dataFrame2StreamingSeriesHolder(dataFrame: DataFrame): StreamingSeriesHolder =
//    new StreamingSeriesHolder(dataFrame)

}

