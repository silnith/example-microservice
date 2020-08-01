package org.silnith.example.microservice.jaxrs;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.silnith.example.microservice.data.DataProvider;
import org.silnith.example.microservice.model.TransactionDetails;
import org.silnith.example.microservice.model.TransactionRequest;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    
    @Mock
    private DataProvider dataProvider;
    
    private TransactionController controller;
    
    @BeforeEach
    public void setUp() {
        controller = new TransactionController(dataProvider);
    }
    
    @Test
    public void testCreateTransaction() throws SQLException {
        final Instant now = Instant.now();
        Mockito.when(dataProvider.getTransactionDetails(Mockito.anyString()))
            .thenReturn(new TransactionDetails("id", "domain", "region", 5, now));
        
        final TransactionRequest request = new TransactionRequest("domain", "region", 5);
        final TransactionDetails details = (TransactionDetails) controller.createTransaction("id", request).getEntity();
        
        assertNotNull(details);
        assertEquals("id", details.getId());
        assertEquals("domain", details.getDomain());
        assertEquals("region", details.getRegion());
        assertEquals(5, details.getCount());
        assertEquals(now, details.getCreated());
        
        Mockito.verify(dataProvider).createTransaction(Mockito.eq("id"), Mockito.eq("domain"), Mockito.eq("region"), Mockito.eq(5));
        Mockito.verify(dataProvider).getTransactionDetails(Mockito.eq("id"));
    }
    
    @Test
    public void testGetTransactionDetails() throws SQLException {
        final Instant now = Instant.now();
        Mockito.when(dataProvider.getTransactionDetails(Mockito.anyString()))
            .thenReturn(new TransactionDetails("id", "domain", "region", 5, now));
    
        final TransactionDetails details = (TransactionDetails) controller.getTransactionDetails("id").getEntity();

        assertNotNull(details);
        assertEquals("id", details.getId());
        assertEquals("domain", details.getDomain());
        assertEquals("region", details.getRegion());
        assertEquals(5, details.getCount());
        assertEquals(now, details.getCreated());
        
        Mockito.verify(dataProvider).getTransactionDetails(Mockito.eq("id"));
    }
    
}
