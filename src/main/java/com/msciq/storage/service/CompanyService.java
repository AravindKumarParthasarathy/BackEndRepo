package com.msciq.storage.service;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.Company;
import com.msciq.storage.model.response.ResponseDTO;

import java.util.List;

public interface CompanyService {

    /**
     * This method will be used to create a company in the given tenant namespace
     *
     * @param company   - entity which should be created in the given tenant namespace
     * @param datastore - datastore object of the tenant namespace where the company entity is created
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    public ResponseDTO saveCompanyInGivenNamespace(Company company, Datastore datastore);

    /**
     * This method will be used to get company entity by tenant and company name
     *
     * @param tenantName - tenant name from where the company should be fetched
     * @param name - name of the company
     *
     * @return Company
     *      company entity from the given tenant namespace
     */
    public Company getCompany(String tenantName, String name);

    /**
     * This method will be used to get the list of company entities by tenant name
     *
     * @param tenantName - tenant name from where the list of companies should be fetched
     *
     * @return List<Company>
     *      list of company entities from the given tenant namespace
     */
    public List<Company> getCompany(String tenantName);

}
