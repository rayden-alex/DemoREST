package com.samsolution.demo.jaxrs.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sayHello")
@Produces(MediaType.APPLICATION_JSON)
public interface HelloService {

    @GET
    @Path("/{a}")
    Response sayHello(@PathParam("a") String a);

}
