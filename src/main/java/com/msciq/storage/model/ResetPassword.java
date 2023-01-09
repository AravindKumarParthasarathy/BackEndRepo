package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder
public class ResetPassword {

    private String requestType;

    @NotBlank(message = "Requester email is mandatory")
    @Email(message = "Email is invalid")
    private String email;

}
