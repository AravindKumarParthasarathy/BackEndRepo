package com.msciq.storage.controller;

import com.msciq.storage.common.Constants;
import com.msciq.storage.common.SuccessMessage;
import com.msciq.storage.model.PermissionObject;
import com.msciq.storage.model.Role;
import com.msciq.storage.model.response.SuccessResponse;
import com.msciq.storage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * <p>
     * Add new role for some action privileges.
     * </p>
     *
     * @param roles - role information to be added
     * @return Role - response of adding the role
     */
    @PostMapping
    @PreAuthorize("hasAuthority('Role_Admin:CREATE')")
    public SuccessResponse<List<Role>> addRole(@RequestBody List<Role> roles) {
        return new SuccessResponse<List<Role>>
                (String.format(SuccessMessage.SUCCESSFULLY_SAVED, Constants.ROLE),
                        roleService.addRole(roles),
                        null,
                        HttpStatus.CREATED);
    }

    /**
     * <p>
     * Retrieve all the active roles detail list.
     * </p>
     *
     * @return List<Role> - List of Role Entity
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Role_Admin:READ') or hasAuthority('User_Role_Admin:READ')")
    public SuccessResponse<List<Role>> getAllRoles() {
        List<Role> roleList =  roleService.getAllRoles();
        return new SuccessResponse<List<Role>>(String.format(SuccessMessage.SUCCESS, Constants.ROLE)
                , roleList, null, HttpStatus.OK);
    }
}
