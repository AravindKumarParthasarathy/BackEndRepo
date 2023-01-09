package com.msciq.storage.model.response;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class LoginResponse {

    private String idToken;

    private boolean isError;

    private String message;
}
