package com.msciq.storage.service;

import com.msciq.storage.model.UserRoleMapping;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRoleMappingService {

    /**
     * This method will be used to create a list of role permission mappings
     *
     * @param userRoleMappings   - list of userRoleMappings which should be created
     *
     * @return ResponseEntity
     *      which has response code and list of created data
     */
    public List<UserRoleMapping> saveAllUserRoleMappings(List<UserRoleMapping> userRoleMappings);

    /**
     * This method will be used to get the list of role permission mappings
     *
     * @return List<UserRoleMapping>
     *      list of UserRoleMappings will be returned
     */
    public List<UserRoleMapping> getUserRoleMappings();

}
