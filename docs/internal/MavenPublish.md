
[Deploy to maven central repository](http://central.sonatype.org/pages/ossrh-guide.html)

[How to publish in Maven](http://central.sonatype.org/pages/apache-maven.html)

mvn clean deploy

mvn versions:set -DnewVersion=1.2.3

mvn deploy -P release

mvn release:prepare
mvn release:perform
