package org.silnith.example.microservice.jaxrs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OpenAPIGeneratorITCase {
    
    @Inject
    private TestRestTemplate restTemplate;
    
    @Test
    public void testOpenAPIDocument() {
        final RequestEntity<?> requestEntity = RequestEntity
                .get(URI.create("/api/openapi.json"))
                .accept(MediaType.APPLICATION_JSON)
                .build();
        
        final ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
    
}
