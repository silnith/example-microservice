package org.silnith.example.microservice.jaxrs;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.silnith.example.microservice.data.DataProvider;
import org.silnith.example.microservice.model.TransactionDetails;
import org.silnith.example.microservice.model.TransactionRequest;
import org.springframework.stereotype.Controller;

@Controller
@Path("transaction")
public class TransactionController {
    
    private final DataProvider dataProvider;

    @Inject
    public TransactionController(@NotNull final DataProvider dataProvider) {
        super();
        this.dataProvider = dataProvider;
    }
    
    @PUT
    @Path("{transactionId}")
    public TransactionDetails createTransaction(@PathParam("transactionId") @NotNull final String id,
            @NotNull @Valid final TransactionRequest request) throws SQLException {
        final int created = dataProvider.createTransaction(id, request.getDomain(), request.getRegion(), request.getCount());
        
        return dataProvider.getTransactionDetails(id);
    }
    
    @GET
    @Path("{transactionId}")
    public TransactionDetails getTransactionDetails(@PathParam("transactionId") @NotNull final String id) throws SQLException {
        return dataProvider.getTransactionDetails(id);
    }
    
}
