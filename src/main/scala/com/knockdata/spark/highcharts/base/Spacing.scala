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

package com.knockdata.spark.highcharts.base

trait Spacing extends IModel {

  def spacing(top: Int, right: Int, bottom: Int, left: Int) =
    append("spacing", List(top, right, bottom, left))

  def spacingBottom(value: Int) =
    append("spacingBottom", value)

  def spacingLeft(value: Int) =
    append("spacingLeft", value)

  def spacingRight(value: Int) =
    append("spacingRight", value)

  def spacingTop(value: Int) =
    append("spacingTop", value)

}
