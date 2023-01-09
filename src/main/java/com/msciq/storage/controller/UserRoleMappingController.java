package com.msciq.storage.controller;

import com.msciq.storage.common.Constants;
import com.msciq.storage.common.SuccessMessage;
import com.msciq.storage.model.RolePermissionMapping;
import com.msciq.storage.model.UserRoleMapping;
import com.msciq.storage.model.response.SuccessResponse;
import com.msciq.storage.service.UserRoleMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userRoleMap")
public class UserRoleMappingController {
    @Autowired
    private UserRoleMappingService userRoleMappingService;

    /**
     * This method is used to fetch the list of userRoleMappings
     *
     * @return ResponseEntity with all the userRoleMappings
     *
     */
    @GetMapping
    public SuccessResponse<List<UserRoleMapping>>  getAllUsers() {
        List<UserRoleMapping> userRoleMappings = userRoleMappingService.getUserRoleMappings();
        return new SuccessResponse<List<UserRoleMapping>>(String.format(SuccessMessage.SUCCESS, Constants.USER_ROLE)
                , userRoleMappings, null, HttpStatus.OK);
    }

    /**
     * This method is used to create the list of userRoleMappings
     *
     * @param userRoleMappings - list of userRoleMappings to be created
     *
     * @return ResponseEntity with created userRoleMappings
     *
     */
    @PostMapping
    public SuccessResponse<List<UserRoleMapping>>  saveAllUsers(@RequestBody List<UserRoleMapping> userRoleMappings) {
        return new SuccessResponse<List<UserRoleMapping>>
                (String.format(SuccessMessage.SUCCESSFULLY_SAVED, Constants.USER_ROLE),
                        userRoleMappingService.saveAllUserRoleMappings(userRoleMappings),
                        null,
                        HttpStatus.CREATED);
    }
}
