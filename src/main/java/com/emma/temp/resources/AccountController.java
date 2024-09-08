/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emma.temp.resources;

import com.emma.temp.entities.User;
import com.emma.temp.model.request.AccountRequest;
import com.emma.temp.model.response.AccountResponse;
import com.emma.temp.service.AccountService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gracias
 */

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    private AccountService userService;

    @GET
    public Response listAllUsers() {
        List<AccountResponse> users = userService.listAllUsers().stream().map(AccountResponse::new).collect(Collectors.toList());
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new AccountResponse(user)).build();
    }

    @GET
    @Path("/email/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        User user = userService.getUserByUsername(email);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found.").build();
        }
        return Response.ok(new AccountResponse(user)).build();
    }

    @POST
    public Response createUser(@Valid AccountRequest accountRequest) {
        if (accountRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User account = userService.createUser(accountRequest);
        return Response.status(Response.Status.CREATED).entity(new AccountResponse(account)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, AccountRequest accountRequest) {
        User account = userService.updateUser(id, accountRequest);
        return Response.ok(new AccountResponse(account)).build();
    }

    @PUT
    @Path("/email/{email}")
    public Response updateUserByUsername(@PathParam("email") String email, AccountRequest accountRequest) {
        User account = userService.updateUserByUsername(email, accountRequest);
        return Response.ok(new AccountResponse(account)).build();
    }
}