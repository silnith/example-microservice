/**
 * Data models used in the REST API.
 * 
 * <p>These models include annotations for JAXP and JSON-B, as well as annotations to add
 * OpenAPI documentation.  They also make use of the Java Validation API.
 * 
 * <p>The JAXP support is enabled by the {@code spring-boot-starter-foo} Maven dependency.
 * 
 * <p>The JSON-B support is enabled by the {@code spring-boot-starter-json} Maven dependency,
 * as well as having a JSON-B implementation loaded.  This example uses {@code org.apache.johnzon:johnzon-jaxrs}.
 * In order for the Jersey implementation to make use of it, two properties must be included
 * in the Spring configuration.
 * 
 * <ol>
 * <li>{@code spring.http.converters.preferred-json-mapper=jsonb}</li>
 * <li>{@code spring.jersey.type=filter}</li>
 * </ol>
 * 
 * <p>The Java Validation API is enabled by including the {@code foo} Maven dependency, and also
 * having a Java Validation API implementation loaded.  By default {@code foo} is used.
 */
package org.silnith.example.microservice.model;
