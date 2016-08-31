FROM openjdk:8

ENV BRANCH  v0.6.0
ENV ZEPPELIN_HOME /usr/zeppelin
ENV ZEPPELIN_HIGHCHART_VERSION 0.6.0

RUN apt-get update \
  && apt-get install -y git curl sed xmlstarlet python-tk \
  && curl -sL http://archive.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz \
   | gunzip \
   | tar x -C /tmp/ \
  && curl --silent --show-error --retry 5 https://bootstrap.pypa.io/get-pip.py | python \
  && pip install pandas \
  && git clone https://github.com/apache/zeppelin.git /tmp/zeppelin \
  && cd /tmp/zeppelin \
  && git checkout $BRANCH \
  && sed -i 's/"angular":/"highcharts": "^4.2.6","angular":/' /tmp/zeppelin/zeppelin-web/bower.json \
  && sed -i 's#"highlightjs": {#"highcharts": {"main": ["highcharts.js","highcharts-more.js","modules/exporting.js","modules/drilldown.js","modules/maps.js"]},"highlightjs": {#' /tmp/zeppelin/zeppelin-web/bower.json \
  && xmlstarlet ed -s /_:project/_:dependencies -t elem -n dependency -v zeppelin-highcharts /tmp/zeppelin/spark-dependencies/pom.xml > pom2.xml \
  && sed -i "s:zeppelin-highcharts:<groupId>com.knockdata</groupId><artifactId>zeppelin-highcharts</artifactId><version>$ZEPPELIN_HIGHCHART_VERSION</version>:" pom2.xml \
  && mv -f pom2.xml /tmp/zeppelin/spark-dependencies/pom.xml \
  && cd /tmp/zeppelin \
  && /tmp/apache-maven-3.3.9/bin/mvn package -Pbuild-distr -Ppyspark -DskipTests \
  && tar xvf /tmp/zeppelin/zeppelin-distribution/target/zeppelin*.tar.gz -C /usr/ \
  && mv /usr/zeppelin* $ZEPPELIN_HOME \
  && mkdir -p $ZEPPELIN_HOME/logs \
  && mkdir -p $ZEPPELIN_HOME/run \
  && apt-get purge -y --force-yes git curl xmlstarlet \
  && apt-get clean autoclean \
  && apt-get autoremove -y --force-yes \
  && rm -rf /tmp/* \
  && rm -rf ~/.m2 ~/.npm ~/.cache \
  && rm -rf /var/lib/{apt,dpkg,cache,log}/

WORKDIR $ZEPPELIN_HOME
CMD ["bin/zeppelin.sh"]
