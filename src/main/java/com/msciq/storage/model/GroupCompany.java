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

@Entity
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class GroupCompany {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonIgnore
    private String id;

    @NotNull(message = "Group company code is mandatory")
    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Currency is mandatory")
    @NotBlank(message = "Currency is mandatory")
    private String currency;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private LocalDateTime createdDt;

    @JsonIgnore
    private String lastUpdatedBy;

    @JsonIgnore
    private LocalDateTime lastUpdatedDt;

    @JsonIgnore
    private boolean isActive;
}
