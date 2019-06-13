package com.samsolution.demo.jaxrs.controller.impl;

import com.samsolution.demo.dto.EmployeeDto;
import com.samsolution.demo.jaxrs.controller.HelloService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDate;

@Api("/sayHello")
@Service
// Warning!:
// No JAX-RS annotation should be included in the implementation class, method or method arguments.
// It must be included in the interface only.
// @Produces(MediaType.APPLICATION_JSON)
public class HelloServiceImpl1 implements HelloService {

    public Response sayHello(String a) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(111L);
        dto.setFirstName(a);
        dto.setLastName(a);
        dto.setBirthday(LocalDate.now());

        return Response.status(Response.Status.OK).entity(dto).build();
    }

}