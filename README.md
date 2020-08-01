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

And the integration test phase is one of the most useful aspects of the Maven
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
