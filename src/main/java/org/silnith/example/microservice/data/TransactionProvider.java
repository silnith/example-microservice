package org.silnith.example.microservice.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.silnith.example.microservice.model.TransactionDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;


/**
 * Repository for persisting and querying transactions.
 */
@Repository
public class TransactionProvider {
    
    private static final String sourceClass = TransactionProvider.class.getName();

    private final Logger logger;

    private final DataSource dataSource;

    private final SQLExceptionTranslator exceptionTranslator;

    /**
     * Creates a new transaction provider.
     * 
     * @param dataSource the data source
     * @param exceptionTranslator an exception translator
     */
    public TransactionProvider(@NotNull final DataSource dataSource, @NotNull final SQLExceptionTranslator exceptionTranslator) {
        super();
        this.logger = Logger.getLogger(sourceClass);
        this.dataSource = dataSource;
        this.exceptionTranslator = exceptionTranslator;
    }
    
    /**
     * Creates a new transaction provider.
     * 
     * @param jdbcTemplate the JDBC template
     */
    @Inject
    public TransactionProvider(@NotNull final JdbcTemplate jdbcTemplate) {
        this(jdbcTemplate.getDataSource(), jdbcTemplate.getExceptionTranslator());
    }

    /**
     * Creates a new transaction record in the database.
     * 
     * @param details the transaction record details
     * @return the number of rows inserted.  Should always be {@code 1}.
     * @throws SQLException if there was a problem communicating with the database
     * @throws org.springframework.dao.DataIntegrityViolationException if the same transaction ID is inserted a second time
     */
    public int createTransaction(@NotNull final TransactionDetails details) throws SQLException {
        final String sourceMethod = "createTransaction";
        logger.entering(sourceClass, sourceMethod, details);
        
        final String transactionId = details.getId();
        final String domain = details.getDomain();
        final String region = details.getRegion();
        final int count = details.getCount();
        final Timestamp timestamp = new Timestamp(details.getCreated().toEpochMilli());

        final int created = createTransaction(transactionId, domain, region, count, timestamp);
        
        logger.exiting(sourceClass, sourceMethod, created);
        return created;
    }

    /**
     * Creates a new transaction record in the database.
     * 
     * @param transactionId the transaction identifier
     * @param domain the domain
     * @param region the region
     * @param count the count
     * @param timestamp the timestamp
     * @return the number of rows inserted.  Should always be {@code 1}.
     * @throws SQLException if there was a problem communicating with the database
     * @throws org.springframework.dao.DataIntegrityViolationException if the same transaction ID is inserted a second time
     */
    public int createTransaction(@NotNull final String transactionId, @NotNull final String domain,
            @NotNull final String region, final int count, @NotNull final Timestamp timestamp) throws SQLException {
        final String sourceMethod = "createTransaction";
        logger.entering(sourceClass, sourceMethod, new Object[] {transactionId, domain, region, count, timestamp,});
        
        final String sql = "insert into \"transactions\" (\"transaction_id\", \"domain\", \"region\", \"count\", \"created\") values (?, ?, ?, ?, ?)";
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, transactionId);
                preparedStatement.setString(2, domain);
                preparedStatement.setString(3, region);
                preparedStatement.setInt(4, count);
                preparedStatement.setTimestamp(5, timestamp);

                final int rowsInserted = preparedStatement.executeUpdate();

                logger.exiting(sourceClass, sourceMethod, rowsInserted);
                return rowsInserted;
            }
        } catch (final SQLException e) {
            final DataAccessException translated = exceptionTranslator.translate("Creating a new transaction record.", sql, e);
            if (translated != null) {
                throw translated;
            } else {
                throw e;
            }
        }
    }

    /**
     * Creates a new transaction record in the database.
     * 
     * @param transactionId the transaction identifier
     * @param domain the domain
     * @param region the region
     * @param count the count
     * @return the number of rows inserted.  Should always be {@code 1}.
     * @throws SQLException if there was a problem communicating with the database
     * @throws org.springframework.dao.DataIntegrityViolationException if the same transaction ID is inserted a second time
     */
    public int createTransaction(@NotNull final String transactionId, @NotNull final String domain,
            @NotNull final String region, final int count) throws SQLException {
        final String sourceMethod = "createTransaction";
        logger.entering(sourceClass, sourceMethod, new Object[] {transactionId, domain, region, count,});
        
        final String sql = "insert into \"transactions\" (\"transaction_id\", \"domain\", \"region\", \"count\") values (?, ?, ?, ?)";
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    sql)) {
                preparedStatement.setString(1, transactionId);
                preparedStatement.setString(2, domain);
                preparedStatement.setString(3, region);
                preparedStatement.setInt(4, count);

                final int rowsInserted = preparedStatement.executeUpdate();

                logger.exiting(sourceClass, sourceMethod, rowsInserted);
                return rowsInserted;
            }
        } catch (final SQLException e) {
            final DataAccessException translated = exceptionTranslator.translate("Creating a new transaction record.", sql, e);
            if (translated != null) {
                throw translated;
            } else {
                throw e;
            }
        }
    }

    /**
     * Reads the transaction record from the database.
     * 
     * @param transactionId the transaction identifier
     * @return the transaction record details, or {@code null}
     * @throws SQLException if there was a problem communicating with the database
     */
    public TransactionDetails getTransactionDetails(@NotNull final String transactionId) throws SQLException {
        final String sourceMethod = "getTransactionDetails";
        logger.entering(sourceClass, sourceMethod, transactionId);
        
        final String sql = "select \"domain\", \"region\", \"count\", \"created\" from \"transactions\" where \"transaction_id\" = ?";
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    sql)) {
                preparedStatement.setString(1, transactionId);

                try (final ResultSet results = preparedStatement.executeQuery()) {
                    while (results.next()) {
                        final String domain = results.getString(1);
                        final String region = results.getString(2);
                        final int count = results.getInt(3);
                        final Timestamp timestamp = results.getTimestamp(4);
                        final Instant instant = Instant.ofEpochMilli(timestamp.getTime());

                        final TransactionDetails transactionDetails = new TransactionDetails(transactionId, domain, region, count, instant);
                        
                        logger.exiting(sourceClass, sourceMethod, transactionDetails);
                        return transactionDetails;
                    }

                    logger.exiting(sourceClass, sourceMethod, null);
                    return null;
                }
            }
        } catch (final SQLException e) {
            /*
             * Yeah, yeah, I know.  If I just used the JDBC template this would not be necessary.
             * Sorry, I don't like the JDBC template.  I like JDBC.  But the exception translation is handy.
             */
            final DataAccessException translated = exceptionTranslator.translate("Reading a transaction record.", sql, e);
            if (translated != null) {
                throw translated;
            } else {
                throw e;
            }
        }
    }

}
