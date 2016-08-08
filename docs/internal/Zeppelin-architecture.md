The following process will be started when we run `bin/zeppelin-daemon start`. 

    /usr/bin/java 
      -Dfile.encoding=UTF-8 -Xms1024m -Xmx1024m -XX:MaxPermSize=512m 
      -Dlog4j.configuration=file://./conf/log4j.properties 
      -Dzeppelin.log.file=./logs/zeppelin-rockieyang-Rockies-MacBook-Pro.local.log 
      -cp :
        :./zeppelin-server/target/lib/*
        :./zeppelin-zengine/target/lib/*
        :./zeppelin-interpreter/target/lib/*
        :./*:
        :./conf
        :./zeppelin-interpreter/target/classes
        :./zeppelin-zengine/target/classes
        :./zeppelin-server/target/classes 
      org.apache.zeppelin.server.ZeppelinServer

It has following class path entries
      
| cp											| description					|
|---------------------------------------|------------------------------------------------------|
|./zeppelin-server/target/lib/* 			| zeppelin-server's dependency libs					|
|./zeppelin-server/target/classes 		| zeppelin-server's own class files      
|./zeppelin-zengine/target/lib/*			| zeppelin-zengine's dependency libs
|./zeppelin-zengine/target/classes		| zeppelin-zengine's own class files
|./zeppelin-interpreter/target/lib/*		| zeppelin-interpreter's dependency libs
|./zeppelin-interpreter/target/classes	| zeppelin-interpreter's own class files
|./*											|
|./conf


The entry point is `org.apache.zeppelin.server.ZeppelinServer`.

`org.apache.zeppelin.server.ZeppelinServer` will start a Jetty Server.

    jettyWebServer = setupJettyServer(conf);

And attach a `NotebookServer` as `ServerletHolder`
    
    notebookWsServer = new NotebookServer();
    String maxTextMessageSize = conf.getWebsocketMaxTextMessageSize();
    final ServletHolder servletHolder = new ServletHolder(notebookWsServer);
    
## REST API

Zeppelin using Jersey for REST support. 

Ref [An example using Jersey](https://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/)

It defines which packages support REST servide in zeppelin-web/src/WEB-INF/web.xml. 

	<servlet>
		<servlet-name>default</servlet-name>
		<servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
		<init-param>
			<param-name>com.sun.jersey.config.property.packages</param-name>
			<param-value>org.apache.zeppelin.rest</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	
All classes in package `org.apache.zeppelin.rest`, they are

	class org.apache.zeppelin.rest.ZeppelinRestApi
	class org.apache.zeppelin.rest.ConfigurationsRestApi
	class org.apache.zeppelin.rest.HeliumRestApi
	class org.apache.zeppelin.rest.LoginRestApi
	class org.apache.zeppelin.rest.InterpreterRestApi
	class org.apache.zeppelin.rest.SecurityRestApi
	class org.apache.zeppelin.rest.NotebookRestApi
	class org.apache.zeppelin.rest.CredentialRestApi
	
Each class defines which path to respond, like HeliumRestApi respond `/helium` with `json` format.

	@Path("/helium")
	@Produces("application/json")
	
the following file should be existed for each interpreter

    src/main/resources/interpreter-setting.json