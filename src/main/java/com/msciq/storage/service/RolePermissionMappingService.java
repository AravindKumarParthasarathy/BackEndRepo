package com.msciq.storage.service;

import com.msciq.storage.model.RolePermissionMapping;
import com.msciq.storage.security.Actions;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RolePermissionMappingService {

    /**
     * This method will be used to create list of role permission mappings
     *
     * @param rolePermissionMappingList   - list of rolePermissionMappings which should be created
     *
     * @return ResponseEntity
     *       which has response code and list of created data
     */
    public List<RolePermissionMapping> saveAllRolePermissionMappings(List<RolePermissionMapping> rolePermissionMappingList);

    /**
     * This method will be used to get the list of role permission mappings
     *
     * @return List<RolePermissionMapping>
     *      list of RolePermissionMappings will be returned
     */
    public List<RolePermissionMapping> getRolePermissionMappings();

    public Map<String, Set<Actions>>  userClaimData(String roleName);

}
