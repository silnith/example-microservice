package org.silnith.example.microservice.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.silnith.example.microservice.model.TransactionDetails;
import org.springframework.stereotype.Repository;

@Repository
public class DataProvider {
    
    private final DataSource dataSource;

    @Inject
    public DataProvider(final DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }
    
    public int createTransaction(final TransactionDetails details) throws SQLException {
        final String transactionId = details.getId();
        final String domain = details.getDomain();
        final String region = details.getRegion();
        final int count = details.getCount();
        final Timestamp timestamp = new Timestamp(details.getCreated().toEpochMilli());
        
        return createTransaction(transactionId, domain, region, count, timestamp);
    }

    public int createTransaction(final String transactionId, final String domain, final String region, final int count,
            final Timestamp timestamp) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into transactions (transaction_id, domain, region, count, created) values (?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, transactionId);
                preparedStatement.setString(2, domain);
                preparedStatement.setString(3, region);
                preparedStatement.setInt(4, count);
                preparedStatement.setTimestamp(5, timestamp);
                
                final int rowsInserted = preparedStatement.executeUpdate();
                
                return rowsInserted;
            }
        }
    }

    public int createTransaction(final String transactionId, final String domain, final String region, final int count) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into transactions (transaction_id, domain, region, count) values (?, ?, ?, ?)")) {
                preparedStatement.setString(1, transactionId);
                preparedStatement.setString(2, domain);
                preparedStatement.setString(3, region);
                preparedStatement.setInt(4, count);
                
                final int rowsInserted = preparedStatement.executeUpdate();
                
                return rowsInserted;
            }
        }
    }
    
    public TransactionDetails getTransactionDetails(final String transactionId) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "select domain, region, count, created from transactions where transaction_id = ?")) {
                preparedStatement.setString(1, transactionId);
                
                try (final ResultSet results = preparedStatement.executeQuery()) {
                    while (results.next()) {
                        final String domain = results.getString(1);
                        final String region = results.getString(2);
                        final int count = results.getInt(3);
                        final Timestamp timestamp = results.getTimestamp(4);
                        final Instant instant = Instant.ofEpochMilli(timestamp.getTime());
                        
                        return new TransactionDetails(transactionId, domain, region, count, instant);
                    }
                    
                    return null;
                }
            }
        }
    }
    
}
