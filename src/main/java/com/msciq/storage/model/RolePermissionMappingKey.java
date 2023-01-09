package com.msciq.storage.model;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RolePermissionMappingKey implements Serializable {

    private String roleName;

    private String permissionObject;

}