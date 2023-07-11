package org.kainos.ea.resources;

import io.swagger.annotations.*;
import org.kainos.ea.api.ProductService;
import org.kainos.ea.cli.*;
import org.kainos.ea.client.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Engineering Academy Dropwizard Product API")
@Path("/api")
public class ProductController {
    private static final String PRODUCTS = "/products";
    private static final String PRODUCT_ID = "/{id}";
    private static final String PRODUCT = "/product";
    private static final String UPDATE_PRODUCT = "/{id}";

    private ProductService productService = new ProductService();

    @GET
    @Path(PRODUCTS)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all products"),
            @ApiResponse(code = 400, message = "Failed to retrieve products list"),
            @ApiResponse(code = 500, message = "Failed to connect with database")
    })
    @ApiOperation(
            value = "Retrieve all products list from the database",
            produces = "application/json"
    )
    public Response getAllProducts() {
        try {
            return Response.ok(productService.getAllProducts()).build();
        } catch (FailedToGetProductsException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Path(PRODUCTS + PRODUCT_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved product by with given ID"),
            @ApiResponse(
                    code = 400,
                    message = "Failed to get product with given ID"
            ),
            @ApiResponse(
                    code = 500,
                    message = "Failed to connect with database"
            )
    })
    @ApiOperation(value = "Retrieve single product by given ID")
    public Response getProductById(
            @PathParam("id") int id
    ) {
        try {
            return Response.ok(productService.getProductById(id)).build();
        } catch (FailedToGetProductException e) {
            return Response.serverError().build();

        } catch (ProductDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path(PRODUCT)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new product"),
            @ApiResponse(code = 400, message = "Failed to create new product"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response createProduct(ProductRequest product) {
        try {
            return Response.ok(productService.createNewProduct(product)).build();
        } catch (FailedToCreateProductException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        } catch (InvalidProductException e) {
            System.out.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path(PRODUCTS + PRODUCT_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated product with the given ID number"),
            @ApiResponse(code = 400, message = "Failed to update product with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connected with the database")
    })
    public Response updateProduct(@PathParam("id") int id, ProductRequest product) {
        try {
            productService.updateProduct(id, product);

            return Response.ok().build();
        } catch (ProductDoesNotExistException | InvalidProductException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (FailedToUpdateProductException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        }
    }


    @DELETE
    @Path(PRODUCTS + PRODUCT_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted product with the given ID number"),
            @ApiResponse(code = 400, message = "Failed to delete product with the given ID number",
                    responseHeaders = {@ResponseHeader(description = "Could not find a product with the given ID")}),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    @ApiOperation(value = "Deletes product with the given ID number")
    public Response deleteProduct(@PathParam("id") int id) {
        try {
            productService.deleteProduct(id);

            return Response.ok().build();
        } catch (ProductDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (FailedToDeleteProductException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        }
    }

}
