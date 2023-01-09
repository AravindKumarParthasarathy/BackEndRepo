package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.*;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "user")
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long id;

    @NotNull(message = "Organization name is mandatory")
    @NotBlank(message = "Organization name is mandatory")
    private String organizationName;

    @NotNull(message = "FirstName is mandatory")
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    private String lastName;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email is invalid")
    private String email;

    private String phoneNumber;

    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @JsonIgnore
    private String userType;

    @JsonIgnore
    private boolean isActive;

    @JsonIgnore
    private boolean isVerified;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdDt;

    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime lastUpdatedDt;

    @JsonIgnore
    @CreatedBy
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastUpdatedBy;
}
