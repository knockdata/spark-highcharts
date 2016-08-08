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

package com.knockdata.zeppelin.highcharts.base

trait Margin extends BaseModel {
  def margin(top: Int, right: Int, bottom: Int, left: Int) =
    append("margin", Seq(top, right, bottom, left))

  def marginBottom(value: Int) = append("marginBottom", value)

  def marginRight(value: Int) = append("marginRight", value)

  def marginLeft(value: Int) = append("marginLeft", value)

  def marginTop(value: Int) = append("marginTop", value)
}
