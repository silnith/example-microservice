# Example Microservice

## Purpose

The purpose of this code repository is to show an example of how to implement a
service (or microservice) in Java using Spring Boot.

### Technologies and Techniques

There are a variety of tools being integrated in this example.

* Spring Boot
    * common properties to modify Spring behavior
    * replacing default providers with alternatives
    * using out-of-the-box tools such as Spring Actuator
* JAX-RS
* JSON-B
* OpenAPI
  * embedded Swagger UI
* Maven
  * useful standard plugins
    * Enforcer plugin
    * Toolchain plugin
  * integration test phase
    * embedded SQL database
    * full servlet initialization and shutdown
    * full serialization and deserialization
  * site generation
    * JavaDoc
    * test reports
    * dependency updates
    * security vulnerabilities

Most of these should be used pretty much everywhere, particularly the OWASP
security vulnerability plugin and the dependency updates plugin.  The enforcer
plugin fails the build if the dependency tree has any conflicts or inconsistencies
in it, ensuring fully reproducible builds and stable artifacts.  The toolchain
plugin makes it easy to use later versions of the JDK safely while still
maintaining a previous version as the minimum requirement.

The integration test phase is one of the most useful aspects of the Maven
tool.  Pretty much any test that normally is done manually can be automated using
this, since it stands up a full servlet container and SQL database and loads the
service just like would happen in a real deployment.  But the integration test
phase automatically cleans up these resources, making it safe and appropriate
for use in automated build systems.

### Licensing

You may have noticed the license is the GPL.  This is intentional.  However, it
should not affect the intended use of this repository, which is to provide
examples of good practices and techniques.  Users should not blindly copy the
entire repository, instead they should read the code and learn from it, and
where appropriate copy small portions applicable to their own project.  This
falls under the category of "fair use", and is entirely legal and acceptable
under US copyright laws.  And since I, the copyright owner, am publicly
endorsing such usage, users should feel secure in doing so.  A good rule of
thumb is if you copy anything that includes my name, you are probably breaking
copyright.  If not, you are probably using it fairly.  As a last resort, you can
always ask me directly.

## Running

### Quick-Start

Run the service.

Visit [http://localhost:8080/webjars/swagger-ui/index.html?url=http://localhost:8080/api/openapi.json](http://localhost:8080/webjars/swagger-ui/index.html?url=http://localhost:8080/api/openapi.json).

### Detailed Instructions

This is a Spring Boot service, so it can be run as a stand-alone application or
as a Java servlet.

## Dependencies

Here are the dependencies needed for each particular feature to function.

### JAX-RS

This uses a JAX-RS implementation for HTTP support.  This example uses Jersey.

* `javax.inject:javax.inject`
* `org.springframework.boot:spring-boot-starter-jersey`
* `org.springframework.boot:spring-boot-starter-web`

### JSON-B

This uses a JSON-B implementation for all JSON serialization and deserialization.
This example uses Apache Johnzon.

* `javax.json.bind:javax.json.bind-api`
* `org.apache.johnzon:johnzon-jaxrs`
* `org.springframework.boot:spring-boot-starter-json` (implicit via transitive)

### JDBC

This provides JDBC database support, which includes the embedded database for
integration testing.

* `org.springframework.boot:spring-boot-starter-jdbc`
* `org.apache.derby:derby`

### OpenAPI

This provides OpenAPI support, specifically the generated endpoints
[/api/openapi.json](http://localhost:8080/api/openapi.json) and
[/api/openapi.yaml](http://localhost:8080/api/openapi.yaml).

* `io.swagger.core.v3:swagger-jaxrs2`
* `io.swagger.core.v3:swagger-jaxrs2-servlet-initializer-v2`

### Swagger UI

This is distinct from the OpenAPI feature.  The former merely generates the
OpenAPI document and endpoint.  Swagger UI provides the HTML and CSS and JS seen
when visiting [/webjars/swagger-ui/index.html](http://localhost:8080/webjars/swagger-ui/index.html?url=http://localhost:8080/api/openapi.json).  Swagger UI is incredibly
useful for development and debugging, but inappropriate to include in a production
service.

* `org.webjars:swagger-ui`
* `org.webjars:webjars-locator-core`

The `webjars-locator-core` dependency enables accessing the Swagger UI resources without the
version number in the URL.
