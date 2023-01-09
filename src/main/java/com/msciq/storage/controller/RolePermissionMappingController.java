package com.msciq.storage.controller;

import com.msciq.storage.common.Constants;
import com.msciq.storage.common.SuccessMessage;
import com.msciq.storage.model.RolePermissionMapping;
import com.msciq.storage.model.RolePermissionMappingDTO;
import com.msciq.storage.model.response.SuccessResponse;
import com.msciq.storage.security.Actions;
import com.msciq.storage.service.RolePermissionMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/rolePermMap")
@Validated
public class RolePermissionMappingController {
    @Autowired
    private RolePermissionMappingService rolePermissionMappingService;

    /**
     * This method is used to fetch the list of rolePermissionMappings
     *
     * @return ResponseEntity with all the rolePermissionMappings
     *
     */
    @GetMapping
    @PreAuthorize("hasAuthority('User_Role_Admin:READ')")
    public SuccessResponse<List<RolePermissionMapping>> getAllMapping() {
        List<RolePermissionMapping> rolePermissionMappingList = rolePermissionMappingService.getRolePermissionMappings();
        return new SuccessResponse<List<RolePermissionMapping>>(String.format(SuccessMessage.SUCCESS, Constants.ROLE_PERMISSION)
                , rolePermissionMappingList, null, HttpStatus.OK);
    }

    /**
     * This method is used to create the list of rolePermissionMappings
     *
     * @param rolePermissionMappingList - list of rolePermissionMappings to be created
     *
     * @return ResponseEntity with created rolePermissionMappings
     *
     */
    @PostMapping
    @PreAuthorize("hasAuthority('User_Role_Admin:CREATE')")
    public SuccessResponse<List<RolePermissionMapping>> saveAllMapping(@RequestBody List<RolePermissionMapping> rolePermissionMappingList) {
        return new SuccessResponse<List<RolePermissionMapping>>
                (String.format(SuccessMessage.SUCCESSFULLY_SAVED, Constants.ROLE_PERMISSION),
                        rolePermissionMappingService.saveAllRolePermissionMappings(rolePermissionMappingList),
                        null,
                        HttpStatus.CREATED);
    }

    /**
     * This method is used to fetch the list of claim object
     *
     * @return ResponseEntity with all the rolePermissionMappings
     *
     */

}
