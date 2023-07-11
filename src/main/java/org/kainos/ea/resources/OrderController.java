package org.kainos.ea.resources;

import io.swagger.annotations.*;
import org.kainos.ea.api.OrderService;
import org.kainos.ea.cli.*;
import org.kainos.ea.client.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Engineering Academy Dropwizard Order API")
@Path("/api")
public class OrderController {
    private static final String ORDERS = "/orders";
    private static final String ORDER_ID = "/{id}";

    private static final String ORDER = "/order";

    private final OrderService orderService = new OrderService();

    @GET
    @Path(ORDERS)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of orders"),
            @ApiResponse(code = 400, message = "Failed to retrieve list of orders"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    @ApiOperation(
            value = "Retrieves all orders from database",
            produces = MediaType.APPLICATION_JSON
    )
    public Response getOrders() {
        try {
            return Response.ok(orderService.getAllOrders()).build();
        } catch (FailedToGetOrdersException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        }
    }

    @GET
    @Path(ORDERS + ORDER_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found order with given ID"),
            @ApiResponse(code = 400, message = "Failed to find order with given ID"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    @ApiOperation(value = "Retrieve single order by given ID")
    public Response getOrderById(@PathParam(value = "id") int id) {
        try {
            return Response.ok(orderService.getOrderById(id)).build();
        } catch (FailedToGetOrdersException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        } catch (OrderDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path(ORDER)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new product in the database"),
            @ApiResponse(code = 400, message = "Failed to create new product in the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    @ApiOperation(value = "Creates new product")
    public Response createNewProduct(OrderRequest order) {
        try {
            return Response.ok(orderService.createNewOrder(order)).build();
        } catch (FailedToCreateOrderException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        }
    }

    @PUT
    @Path(ORDERS + ORDER_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated order with the given ID number"),
            @ApiResponse(code = 400, message = "Failed to update order with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    @ApiOperation(value = "Updates product with the given ID number")
    public Response updateProduct(@PathParam("id") int id, OrderRequest order) {
        try {
            orderService.updateOrder(id, order);

            return Response.ok().build();
        } catch (OrderDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (FailedToUpdateOrderException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path(ORDERS + ORDER_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted order with the given ID number"),
            @ApiResponse(code = 400, message = "Failed to delete order with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    } )
    @ApiOperation(value = "Deletes order with the given ID number")
    public Response deleteOrder(@PathParam("id") int id) {
        try {
            orderService.deleteProduct(id);
            return Response.ok().build();
        } catch (OrderDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (FailedToDeleteOrderException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        }
    }
}
