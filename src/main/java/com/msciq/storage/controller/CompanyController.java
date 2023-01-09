package com.msciq.storage.controller;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.Company;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.CompanyService;
import com.msciq.storage.service.GCPStorageService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import com.msciq.storage.validator.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fpa")
public class CompanyController {

    @Autowired
    GCPStorageService gcpStorageService;

    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyValidator companyValidator;

    /**
     * This method is used to create the company
     *
     * @param company - company model
     * @return successful message for the company creation
     */
    @PostMapping("/{organizationName}/company/create")
    public ResponseDTO createCompany(@PathVariable String organizationName,
                                     @RequestBody @Valid Company company) {
        try{
            validateCompanyRequest(company);
            if(companyService.getCompany(organizationName, company.getName()) == null) {
                String namespaceName = Constants.ORGANIZATION_PREFIX + organizationName.toLowerCase().replaceAll("\\s", "_");
                String folderName = Constants.COMPANY_PREFIX + company.getName().toLowerCase().replaceAll("\\s", "") + "/";
                //This below method will create a folder inside the tenant for the company
                gcpStorageService.createFolderInsideBucket(namespaceName, folderName);
                Datastore datastore = gcpStorageService.createOrGetNamespace(namespaceName);
                return companyService.saveCompanyInGivenNamespace(company, datastore);
            }else{
                return ResponseDTO.builder()
                        .message(String.format(ErrorMessage.COMPANY_ALREADY_EXISTS, company.getName()))
                        .isError(true)
                        .build();
            }
        } catch (Exception exception) {
            return ResponseDTO.builder()
                    .message(ErrorMessage.COMPANY_ERROR + exception.getMessage())
                    .isError(true)
                    .build();
        }
    }

    /**
     *
     * This method is used to retrieve the company details
     *
     * @param name - name of the company
     * @return company model with the details
     */
    @GetMapping("/{organizationName}/company/get/{name}")
    public Company getCompany(@PathVariable String organizationName,
            @PathVariable String name
    ) {
        return companyService.getCompany(organizationName, name);
    }

    /**
     *
     * This method is used to retrieve the company details
     *
     * @return list of companies with the details
     */
    @GetMapping("/{organizationName}/get/companies")
    public List<Company> getCompany(@PathVariable String organizationName
    ) {
        return companyService.getCompany(organizationName);
    }

    private void validateCompanyRequest(Company company) {
        companyValidator.checkIfCompanyNameIsValid(company.getName());
        companyValidator.checkIfCompanyCodeIsValid(company.getCode());
        companyValidator.checkIfGroupCompanyNameIsValid(company.getGroupCompanyCode());
        companyValidator.checkIfCompanyCurrencyIsValid(company.getCurrency());
        companyValidator.checkIfCompanyFiscalYearIsValid(company.getFiscalYear());
    }

}
