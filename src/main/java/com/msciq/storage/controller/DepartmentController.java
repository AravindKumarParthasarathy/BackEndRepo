package com.msciq.storage.controller;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.Department;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.DepartmentService;
import com.msciq.storage.service.impl.GCPStorageServiceImpl;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.validator.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fpa")
public class DepartmentController {

    @Autowired
    GCPStorageServiceImpl gcpStorageService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentValidator departmentValidator;
    Datastore datastore;

    /**
     * This method is used to create the department
     *
     * @param department - model
     * @return successful message with created department code
     */
    @PostMapping("/{organizationName}/department/create")
    public ResponseDTO createDepartment(@PathVariable String organizationName,
                                        @RequestBody Department department
    ) {
        try{
            validateDepartmentRequest(department);
            if(departmentService.getDepartment(organizationName, department.getName())==null){
                datastore = gcpStorageService.createOrGetNamespace(Constants.ORGANIZATION_PREFIX + organizationName.toLowerCase().replaceAll("\\s", "_"));
                return departmentService.saveDeptInGivenNamespace(department, datastore);
            }else{
                return ResponseDTO.builder()
                        .message(String.format(ErrorMessage.DEPARTMENT_ALREADY_EXISTS, department.getName()))
                        .isError(true)
                        .build();
            }
        } catch (Exception exception) {
            return ResponseDTO.builder()
                    .message(ErrorMessage.DEPARTMENT_ERROR + exception.getMessage())
                    .isError(true)
                    .build();
        }

    }

    /**
     * This method is used to retrieve the details of the given department
     *
     * @param name - name of the department
     * @return department details
     */
    @GetMapping("{organizationName}/department/get/{name}")
    public Department getDepartment(@PathVariable String organizationName,
            @PathVariable String name
    ) {
        return departmentService.getDepartment(organizationName, name);
    }

    /**
     * This method is used to retrieve  all the departments from the organization
     *
     * @param organizationName - name of the organization
     * @return department details
     */
    @GetMapping("{organizationName}/get/departments")
    public List<Department> getDepartment(@PathVariable String organizationName
    ) {

        return departmentService.getDepartment(organizationName);
    }

    private void validateDepartmentRequest(Department department) {
        departmentValidator.checkIfDepartmentNameIsValid(department.getName());
        departmentValidator.checkIfDepartmentCodeIsValid(department.getCode());
    }
}
