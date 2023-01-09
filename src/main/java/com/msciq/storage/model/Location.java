package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.*;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
@Entity
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class Location {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonIgnore
    private String id;

    private String companyCode;

    @NonNull
    @NotBlank(message="Location code is mandatory")
    private String code;

    @NonNull
    @NotBlank(message="Location name is mandatory")
    private String name;

    @NonNull
    @NotBlank(message="Location address is mandatory")
    private String address;

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
