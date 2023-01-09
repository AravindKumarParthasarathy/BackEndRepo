package com.msciq.storage.service;

import com.msciq.storage.model.PermissionObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissionService {

    /**
     * This method will be used to create list of permission
     *
     * @param permissionObjects   - list of permissionObjects which should be created
     *
     * @return ResponseEntity
     *       which has response code and list of created data
     */
    public List<PermissionObject> saveAllPermissions(List<PermissionObject> permissionObjects);

    /**
     * This method will be used to get the list of role permission
     *
     * @return ResponseEntity
     *       which has response code and list of created data
     */
    public List<PermissionObject> getAllPermissions();

}
