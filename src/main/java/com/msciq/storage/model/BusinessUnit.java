package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.*;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "businessunit")
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class BusinessUnit {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonIgnore
    private String id;

    @NotNull(message="Group Company code is mandatory")
    @NotBlank(message="Group Company code is mandatory")
    private String groupCompanyCode;

    @NotNull(message="Business Unit code is mandatory")
    @NotBlank(message="Business Unit code is mandatory")
    private String code;

    @NotNull(message="Business Unit Name is mandatory")
    @NotBlank(message="Business Unit Name is mandatory")
    private String name;

    @JsonIgnore
    private LocalDateTime activeFromDate;

    @JsonIgnore
    private boolean isActive;

    @JsonIgnore
    private LocalDateTime inactiveDate;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private LocalDateTime createdDt;

    @JsonIgnore
    private String lastUpdatedBy;

    @JsonIgnore
    private LocalDateTime lastUpdatedDt;
}
