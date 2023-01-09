package com.msciq.storage.controller;

import com.msciq.storage.common.Constants;
import com.msciq.storage.common.SuccessMessage;
import com.msciq.storage.model.PermissionObject;
import com.msciq.storage.model.response.SuccessResponse;
import com.msciq.storage.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * This method is used to fetch the list of Permission
     *
     * @return ResponseEntity with all the Permission
     *
     */
    @GetMapping
    public ResponseEntity getAllPermissions() {
        List<PermissionObject> permissionObjectList = permissionService.getAllPermissions();
        return new SuccessResponse<List<PermissionObject>>(String.format(SuccessMessage.SUCCESS, Constants.PERMISSION)
                , permissionObjectList, null, HttpStatus.OK);
    }

    /**
     * This method is used to create the list of Permission
     *
     * @param permissionObjects - list of permissionObjects to be created
     *
     * @return ResponseEntity with created Permission
     *
     */
    @PostMapping
    public SuccessResponse<List<PermissionObject>> saveAllPermissions(@RequestBody List<PermissionObject> permissionObjects) {
        return new SuccessResponse<List<PermissionObject>>
                (String.format(SuccessMessage.SUCCESSFULLY_SAVED, Constants.PERMISSION),
                        permissionService.saveAllPermissions(permissionObjects),
                        null,
                        HttpStatus.CREATED);
    }
}
