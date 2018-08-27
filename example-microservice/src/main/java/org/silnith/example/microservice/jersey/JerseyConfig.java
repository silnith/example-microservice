package org.silnith.example.microservice.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.silnith.example.microservice.jaxrs.TransactionController;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        super(TransactionController.class);
        super.packages("io.swagger.sample.resource", "io.swagger.v3.jaxrs2.integration.resources");
    }

}
