package com.samsolution.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "EMPLOYEE")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @SequenceGenerator(name = "EMPLOYEE_GEN", sequenceName = "EMPLOYEE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_GEN")
    @Column(updatable = false)
    private Long id;

    @NotEmpty                              //validation
    @Size(max = 50)                        //validation
    @Column(length = 50, nullable = false) //definition
    private String firstName;

    @NotEmpty
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String lastName;

    private LocalDate birthday;

    @CreatedBy
    @Column(length = 50, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime created;

    @LastModifiedBy
    @Column(length = 50)
    protected String lastModifiedBy;

    @LastModifiedDate
    protected LocalDateTime modified;
}
