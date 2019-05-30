package com.samsolution.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    private LocalDate birthday;

}
