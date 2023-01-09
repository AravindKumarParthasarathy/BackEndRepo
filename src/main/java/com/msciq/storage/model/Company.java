package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.*;
import org.springframework.data.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "company")
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class Company {

    @Id
    @JsonIgnore
    private String id;

    private String groupCompanyCode;

    @NotNull
    @NotBlank(message="Company code is mandatory")
    private String code;

    @NotNull
    @NotBlank(message="Company Name is mandatory")
    private String name;

    @NotNull
    @NotBlank(message="Currency is mandatory")
    private String currency;

    @NotNull
    @NotBlank(message="Fiscal year is mandatory")
    private String fiscalYear;

    private LocalDateTime activeFromDate;
    private boolean isActive;
    private LocalDateTime inActiveDate;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private LocalDateTime createdDt;

    @JsonIgnore
    private String lastUpdatedBy;

    @JsonIgnore
    private LocalDateTime lastUpdatedDt;
}
