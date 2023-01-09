package com.msciq.storage.service;

import com.msciq.storage.model.Organization;
import com.msciq.storage.model.response.ResponseDTO;

public interface OrganizationService {

    /**
     * This method is used to create a new Tenant in Google cloud
     *
     * @param organization
     *          - Tenant object which has tenantName, tenantUserEmail and requesterEmail
     *
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    ResponseDTO createOrganization(Organization organization);
}
