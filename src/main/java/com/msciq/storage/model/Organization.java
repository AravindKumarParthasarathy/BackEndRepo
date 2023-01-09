package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder
public class Organization {

    @NotNull(message = "Organization name is mandatory")
    @NotBlank(message = "Organization name is mandatory")
    private String organizationName;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "First Name is mandatory")
    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotNull(message = "Last Name is mandatory")
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

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
