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
 * 
 * <p>There are actually two DI containers in the application, Spring and JAX-RS.  The Spring-Jersey
 * integration makes all of the Spring beans available to the JAX-RS container, so JAX-RS resources
 * can have Spring resources injected into them.
 */
@Configuration
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        super();
        /*
         * This registers the OpenAPI components so that the OpenAPI document is automatically
         * generated and exposed as an endpoint.
         */
        registerClasses(OpenApiResource.class);
        /*
         * This configures the JAX-RS implementation to perform package scanning to find all available
         * JAX-RS resources, including controllers, filters, and type converters.
         * Note that the JAX-RS dependency registration and instantiation is distinct from the
         * Spring container.  This is why the controller does not need to be annotated as a
         * Spring bean.  However, because of the Spring-Jersey integration, Spring beans are still
         * available to be injected into JAX-RS resources.  This is why the
         * TransactionController can have a TransactionProvider injected into it.
         */
        packages(Microservice.class.getPackage().getName());
        
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, false);
        property(ServerProperties.TRACING, "OFF");
//        property(ServletProperties.FILTER_FORWARD_ON_404, true);
//        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/webjars/.*");
//        Resource.builder("swagger-ui").addMethod().handledBy(null);
    }

}
