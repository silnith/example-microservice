package org.silnith.example.microservice.jaxrs;

import java.net.URI;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.silnith.example.microservice.data.TransactionProvider;
import org.silnith.example.microservice.model.TransactionDetails;
import org.silnith.example.microservice.model.TransactionRequest;
import org.springframework.dao.DataIntegrityViolationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@Path("transaction")
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
public class TransactionController {
    
    private static final String sourceClass = TransactionController.class.getName();

    private final Logger logger;
    
    private final TransactionProvider dataProvider;

    @Inject
    public TransactionController(@NotNull final TransactionProvider dataProvider) {
        super();
        this.logger = Logger.getLogger(sourceClass);
        this.dataProvider = dataProvider;
    }
    
    @Operation(
            summary = "Creates a new transaction.",
            description = "Creates a new transaction record in the database.  This operation is idempotent.",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "201", description = "The record was created.", content = {}),
                    @ApiResponse(responseCode = "304", description = "The record already existed.", content = {}),
                    @ApiResponse(responseCode = "409", description = "The record conflicts with the existing transaction."),
            })
    @PUT
    @Path("{transactionId}")
    public Response createTransaction(@PathParam("transactionId") @NotNull final String id,
            @NotNull @Valid final TransactionRequest request) throws SQLException {
        final String sourceMethod = "createTransaction";
        logger.entering(sourceClass, sourceMethod, new Object[] {id, request,});
        
        final int created;
        try {
            created = dataProvider.createTransaction(id, request.getDomain(), request.getRegion(), request.getCount());
        } catch (final DataIntegrityViolationException e) {
            final TransactionDetails existing = dataProvider.getTransactionDetails(id);
            
            assert id == existing.getId();
            
            final Response response;
            if (request.getDomain().equals(existing.getDomain())
                    && request.getRegion().equals(existing.getRegion())
                    && request.getCount() == existing.getCount()) {
                response = Response.status(Response.Status.FOUND).contentLocation(URI.create(id)).build();
            } else {
                response = Response.status(Response.Status.CONFLICT).build();
            }
            
            logger.exiting(sourceClass, sourceMethod, response);
            return response;
        }
        
        final TransactionDetails transactionDetails = dataProvider.getTransactionDetails(id);
        
        final Response response = Response.status(Response.Status.CREATED).entity(transactionDetails).contentLocation(URI.create(id)).build();
        
        logger.exiting(sourceClass, sourceMethod, response);
        return response;
    }
    
    /**
     * Reads and returns the details of the record for a transaction.
     * 
     * @param id the transaction identifier
     * @return the transaction, or a 404 response
     * @throws SQLException
     */
    @Operation(
            summary = "Retrieves a transaction record.",
            description = "Reads and returns the details of the record for a transaction.",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200", description = "The transaction was found.", content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionDetails.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "normal",
                                                    summary = "A normal response.",
                                                    description = "A typical response with example values.",
                                                    value = "{\"id\":\"foobar\",\"domain\":\"eureka\",\"region\":\"sadness\",\"count\":20,\"created\":\"2020-01-01T12:00:00.000Z\"}"),
                                    }),
                            @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = TransactionDetails.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "normal",
                                                    summary = "A normal response.",
                                                    description = "A typical response with example values.",
                                                    value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><transactionDetails>foobar<id></id><domain>eureka</domain><region>sadness</region><count>20</count><created>2020-01-01T12:00:00.000Z</created></transactionDetails>"),
                                    }),
                    }),
                    @ApiResponse(responseCode = "404", description = "The transaction was not found."),
            },
            tags = {"transaction",})
    @GET
    @Path("{transactionId}")
    public Response getTransactionDetails(
            @Parameter(description = "The transaction identifier.")
            @PathParam("transactionId") @NotNull final String id) throws SQLException {
        final String sourceMethod = "getTransactionDetails";
        logger.entering(sourceClass, sourceMethod, id);
        
        final TransactionDetails transactionDetails = dataProvider.getTransactionDetails(id);
        
        final Response response;
        if (transactionDetails == null) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            response = Response.ok(transactionDetails).build();
        }
        
        logger.exiting(sourceClass, sourceMethod, response);
        return response;
    }
    
}
