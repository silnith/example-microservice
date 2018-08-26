package org.silnith.example.microservice.data;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.silnith.example.microservice.model.TransactionDetails;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataProviderTest {
    
    @Inject
    private DataProvider dataProvider;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testCreateTransactionTransactionDetails() throws SQLException {
        dataProvider.createTransaction(new TransactionDetails("id", "domain", "region", 5, Instant.now()));
    }
    
    @Test
    public void testCreateTransactionStringStringStringIntTimestamp() throws SQLException {
        dataProvider.createTransaction("id", "domain", "region", 5, new Timestamp(System.currentTimeMillis()));
    }
    
    @Test
    public void testCreateTransactionStringStringStringInt() throws SQLException {
        dataProvider.createTransaction("id", "domain", "region", 5);
    }
    
    @Test
    public void testGetTransactionDetails() throws SQLException {
        final TransactionDetails transactionDetails = dataProvider.getTransactionDetails("id");
    }
    
}
