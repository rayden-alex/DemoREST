package com.samsolution.demo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Details about the Employee model")
@Data
public class EmployeeDto {
    private Long id;

    @NotEmpty
    @Length(min = 3, max = 50)
    private String firstName;

    @NotEmpty
    @Length(min = 3, max = 50)
    private String lastName;

    @NotNull
//    We don't need to use this annotation when use com.fasterxml.jackson.databind.ObjectMapper
//    from Spring context as JAX-RS provider
//    @see com.samsolution.demo.config.CXFConfig.rsServer
//
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate birthday;

}
