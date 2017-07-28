# Axis

| chart | axis |
|-------|------|
|area            |x,y
|arearange       |x,low,high
|areaspline      |x,y
|areasplinerange |x,low,high
|bar             |x,y
|boxplot         |x,low,q1,medium,q3,high
|bubble          |x,y,z
|column          |x,y
|errorbar        |x,low,high
|funnel          |y
|heatmap         |x,y,value
|line            |x,y
|pie             |y
|polygon         |x,y
|pyramid         |y
|scatter         |x,y
|solidgauge      |y
|spline          |x,y
|treemap         |value
|waterfall       |x,y

| chart | axis |
|-------|------|
|area, areaspline,line            |x,y
|arearange, areasplinerange       |x,low,high
|bar             |x,y
|boxplot         |x,low,q1,medium,q3,high
|bubble          |x,y,z
|column          |x,y
|errorbar        |x,low,high
|funnel          |y
|heatmap         |x,y,value
|pie             |y
|polygon         |x,y
|pyramid         |y
|scatter         |x,y
|solidgauge      |y
|spline          |x,y
|treemap         |value
|waterfall       |x,y

y

    funnel
    pie
    pyramid
    solidgauge

x,y

    area
    bar
    column
    line
    polygon
    scatter
    spline
    waterfall

x,y,z

    bubble

x,low,high

    arearange
    areasplinerange
    columnrange
    errorbar

x,y,value

    heatmap

x,low,q1,medium,q3,high

    boxplot

# 1D

## Pie
only 1 D to represent share in the total

> TODO drilldown with [Pie Donut](http://www.highcharts.com/demo/pie-donut)

## [Activity Gauge](http://www.highcharts.com/demo/gauge-activity)
Good for represent status

## [Error Bars](http://www.highcharts.com/demo/error-bar)
Good for represent

> expected total test case

> actual total test case

> successful case.

# 3D (if used in 2D, then x,y)

x axis, y axis & z color

## Line, Area, Bar, Column

represent 3D, x,y and color

### Using 1D values

> In this case, the interval for point is the same on the line
>
> It is good to use when there aren't many categories
>
> Using 2D can fully cover this case(data size will getting bigger)

* x axis specify categories(/automatic by indexes)
* series specify 1D values, each value correspond to the x category by index
* different series will be rendered in different color


### Using 2D values

> In this case, the interval for point can be different
>
> It is more general so it is chosen for spark-highchart
>


* series specify 2D values for x and y
* different series will be rendered in different color
* Missing point specify the 2nd D value to null

## Scatter Plot

x,y, shape

# 4D (if used as 3D, then x,y with range)

## Bar and Column
x, y using range, color

* Using 2D values with x specifying categories
* Using 3D values with 1st D specifying categories

## Bubble
x,y, size, color,
