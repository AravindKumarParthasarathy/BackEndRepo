package com.msciq.storage.model;

import lombok.Data;

@Data
public class RolePermissionMappingDTO {

    private String roleName;
    private String permissionObject;
    private boolean canRead;
    private boolean canUpdate;
    private boolean canWrite;
    private boolean canDelete;
    private ControlData controlData;
}
