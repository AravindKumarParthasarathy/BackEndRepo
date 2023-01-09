package com.msciq.storage.model.request;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.*;

import javax.enterprise.inject.Model;

@ToString
@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class LoginDTO {

    private String email;

    private String password;

    private boolean returnSecureToken;
}
