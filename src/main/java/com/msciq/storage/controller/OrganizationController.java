package com.msciq.storage.controller;

import com.msciq.storage.model.Organization;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/fpa")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    /**
     * This method is used to create a new Tenant
     *
     * @param organization
     *          - Tenant object which has organization name and email
     *
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    @PostMapping ("/organization/create")
    public ResponseDTO createTenant(
            @Valid @RequestBody Organization organization
    ) {
        return organizationService.createOrganization(organization);
    }

}
