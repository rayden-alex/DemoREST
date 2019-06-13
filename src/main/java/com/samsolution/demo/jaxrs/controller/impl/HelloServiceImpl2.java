package com.samsolution.demo.jaxrs.controller.impl;

import com.samsolution.demo.jaxrs.controller.HelloService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/sayHello2")
@Api("/sayHello2")
@Service
public class HelloServiceImpl2 implements HelloService {

    public Response sayHello(String a) {
        String str = "Hello2 " + a + ", Welcome to CXF RS Spring Boot World!!!";
        // When "String" is returned then it is not serialized.
        // We need wrap it into DTO to get serialized response
        // (@see com.samsolution.demo.jaxrs.controller.impl.HelloServiceImpl1)
        return Response.status(Response.Status.OK).entity(str).build();
    }

}
