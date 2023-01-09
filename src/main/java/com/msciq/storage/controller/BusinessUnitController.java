package com.msciq.storage.controller;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.BusinessUnit;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.BusinessUnitService;
import com.msciq.storage.service.impl.GCPStorageServiceImpl;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.validator.BusinessUnitValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fpa")
public class BusinessUnitController {

    @Autowired
    GCPStorageServiceImpl gcpStorageService;

    @Autowired
    BusinessUnitService businessUnitService;

    @Autowired
    BusinessUnitValidator businessUnitValidator;

    /**
     * This method is used to create the businessUnit
     *
     * @param businessUnit - businessUnit model
     * @return successful message for the businessUnit creation
     */
    @PostMapping("/{organizationName}/businessUnit/create")
    public ResponseDTO createBusinessUnit(@PathVariable String organizationName,
                                          @RequestBody @Valid BusinessUnit businessUnit
    ) {
        validateBusinessUnitRequest(businessUnit);
        try{
            if(businessUnitService.getBusinessUnit(organizationName, businessUnit.getName()) == null) {
                Datastore datastore = gcpStorageService.createOrGetNamespace(Constants.ORGANIZATION_PREFIX + organizationName.toLowerCase().replaceAll("\\s", "_"));
                return businessUnitService.saveBusinessUnitInGivenNamespace(businessUnit, datastore);
            }else{
                return ResponseDTO.builder()
                        .message(String.format(ErrorMessage.BUSINESS_UNIT_ALREADY_EXISTS, businessUnit.getName()))
                        .isError(true)
                        .build();
            }
        }catch(Exception exception){
            return ResponseDTO.builder()
                    .message(ErrorMessage.BUSINESS_UNIT_ERROR + exception.getMessage())
                    .isError(true)
                    .build();
        }
    }

    /**
     * This method is used to retrieve a BusinessUnit from the given tenant
     *
     * @param name - name of the BusinessUnit
     * @Param organizationName - name of the Tenant
     * @return BusinessUnit details will be returned
     *
     */
    @GetMapping("/{organizationName}/get/businessUnit/{name}")
    public BusinessUnit getBusinessUnit(@PathVariable String organizationName,
                                @PathVariable String name
    ) {
        return businessUnitService.getBusinessUnit(organizationName, name);
    }

    /**
     * This method is used to retrieve the list of BusinessUnit from the given tenant
     *
     * @Param organizationName - name of the Tenant
     * @return BusinessUnit details will be returned
     *
     */
    @GetMapping("/{organizationName}/get/businessUnits}")
    public List<BusinessUnit> getBusinessUnit(@PathVariable String organizationName
    ) {
        return businessUnitService.getBusinessUnit(organizationName);
    }

    private void validateBusinessUnitRequest(BusinessUnit businessUnit) {
        businessUnitValidator.checkIfGroupCompanyNameIsValid(businessUnit.getGroupCompanyCode());
        businessUnitValidator.checkIfBusinessUnitCodeIsValid(businessUnit.getCode());
        businessUnitValidator.checkIfBusinessUnitNameIsValid(businessUnit.getName());
    }
}
