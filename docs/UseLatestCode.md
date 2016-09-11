# Use latest code

### Build the binary

    git clone https://github.com/knockdata/spark-highcharts.git
    cd spark-highcharts
    mvn clean package -DskipTests

### Add the binary to Zeppelin

> Goto Zeppelin -> `Interpreters`

> Scroll down to find `spark`

> Click `Edit`

![zeppelin-spark-interpreter-edit](docs/zeppelin-spark-interpreter-edit.png)

> Scroll down to `Dependencies`

> Edit the `artifact` with the correct jar file

> Click `Save`

![zeppelin-spark-interpreter-edit](docs/zeppelin-spark-interpreter-add-jar.png)
