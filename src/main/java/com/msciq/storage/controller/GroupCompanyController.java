package com.msciq.storage.controller;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.GroupCompany;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.GCPStorageService;
import com.msciq.storage.service.GroupCompanyService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.validator.GroupCompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fpa")
public class GroupCompanyController {

    @Autowired
    GCPStorageService gcpStorageService;

    @Autowired
    GroupCompanyService groupCompanyService;

    @Autowired
    GroupCompanyValidator groupCompanyValidator;

    /**
     * This method will be used to creating a new group company and the respective
     * bucket into the gcp
     *
     * @param organizationName   - master company name under which the group company should be created
     * @param groupCompany - group company object which needs to be created
     * @return ResponseDTO
     * which has message and a isError flag
     * if isError flag is true the message has the error message
     */
    @PostMapping("{organizationName}/groupCompany/create")
    public ResponseDTO createCompany(
            @PathVariable String organizationName,
            @RequestBody @Valid GroupCompany groupCompany
    ) {
        validateGroupCompanyRequest(groupCompany);
        try {
            if (groupCompanyService.getGroupCompanyFromTenantByName(organizationName, groupCompany.getName()) == null) {
                String namespaceName = Constants.ORGANIZATION_PREFIX + organizationName.toLowerCase().replaceAll("\\s", "_");
                String folderName = Constants.GROUP_COMPANY_PREFIX + groupCompany.getName().toLowerCase().replaceAll("\\s", "") + "/";
                gcpStorageService.createFolderInsideBucket(namespaceName, folderName);
                Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(namespaceName);
                return groupCompanyService.saveGroupCompanyInGivenNamespace(groupCompany, tenantDatastore);
            } else {
                return ResponseDTO.builder()
                        .message(String.format(ErrorMessage.GROUP_COMPANY_ALREADY_EXISTS, groupCompany.getName()))
                        .isError(true)
                        .build();
            }
        } catch (Exception exception) {
            return ResponseDTO.builder()
                    .message(ErrorMessage.GROUP_COMPANY_ERROR + exception.getMessage())
                    .isError(true)
                    .build();
        }
    }

    /**
     * This method will be used to get the list of group companies by tenant name
     *
     * @param tenantName - tenant name from where the list of companies should be fetched
     * @return List<GroupCompany>
     * list of group company objects which exists under the given tenant
     */
    @GetMapping("/{tenantName}/groupCompanies")
    public List<GroupCompany> getGroupCompany(
            @PathVariable String tenantName
    ) {
        return groupCompanyService.getAllGroupCompaniesFromTenant(tenantName);
    }

    /**
     * This method will be used to get the group company under a tenant name
     *
     * @param tenantName       - tenant name from where the group company should be fetched
     * @param groupCompanyName - name of the group company that should be fetched
     * @return GroupCompany
     * object which has the group company details
     */
    @GetMapping("/{tenantName}/groupCompany/{groupCompanyName}")
    public GroupCompany getGroupCompany(
            @PathVariable String tenantName,
            @PathVariable String groupCompanyName
    ) {
        return groupCompanyService.getGroupCompanyFromTenantByName(tenantName, groupCompanyName);
    }

    /**
     * This method will be used to update the group company entity in tenant namespace
     *
     * @param organizationName - tenant name from where the group company information should be fetched
     * @param groupCompanyName - name of the group company which should be updated
     * @param groupCompany - entity which should be updated in the given tenant namespace
     *
     * @return ResponseDTO
     *      which has message and an error flag
     *      if error flag is true the message has the error message
     */
    @RequestMapping(
            path = "/update/groupCompany",
            method = {RequestMethod.PUT}
    )
    public ResponseDTO updateGroupCompanyByName(
            @RequestParam String organizationName,
            @RequestParam String groupCompanyName,
            @RequestBody GroupCompany groupCompany
    ) {
        if (groupCompany.getCode() != null) {
            groupCompanyValidator.checkIfGroupCompanyCodeIsValid(groupCompany.getCode());
        }
        if (groupCompany.getName() != null) {
            groupCompanyValidator.checkIfGroupCompanyNameIsValid(groupCompany.getName());
        }
        if (groupCompany.getCurrency() != null) {
            groupCompanyValidator.checkIfGroupCompanyCurrencyIsValid(groupCompany.getCurrency());
        }
        return groupCompanyService.updateGroupCompanyByCompanyName(organizationName, groupCompanyName, groupCompany);
    }

    private void validateGroupCompanyRequest(GroupCompany groupCompany) {
        groupCompanyValidator.checkIfGroupCompanyCodeIsValid(groupCompany.getCode());
        groupCompanyValidator.checkIfGroupCompanyNameIsValid(groupCompany.getName());
        groupCompanyValidator.checkIfGroupCompanyCurrencyIsValid(groupCompany.getCurrency());
    }

}
