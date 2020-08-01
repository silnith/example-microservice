package org.silnith.example.microservice.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.silnith.example.microservice.Microservice;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

/**
 * The configuration for the JAX-RS components.
 * 
 * <p>It is crucial that this is annotated with {@link Configuration} and not {@link org.springframework.stereotype.Component}.
 * Otherwise the JAX-RS subsystem will not be initialized properly.
 */
@Configuration
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        super(OpenApiResource.class);
        packages(Microservice.class.getPackage().getName());
        
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, false);
        property(ServerProperties.TRACING, "OFF");
//        property(ServletProperties.FILTER_FORWARD_ON_404, true);
//        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/webjars/.*");
//        Resource.builder("swagger-ui").addMethod().handledBy(null);
    }

}
