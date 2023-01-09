package com.msciq.storage.service;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.BusinessUnit;
import com.msciq.storage.model.response.ResponseDTO;

import java.util.List;

public interface BusinessUnitService {

    /**
     * This method will be used to create a Business Unit in the given tenant namespace
     *
     * @param businessUnit   - entity which should be created in the given tenant namespace
     * @param datastore - datastore object of the tenant namespace where the Business Unit entity is created
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    public ResponseDTO saveBusinessUnitInGivenNamespace(BusinessUnit businessUnit, Datastore datastore);

    /**
     * This method will be used to get the Business Unit entity by tenant and business unit name
     *
     * @param tenantName - tenant name from where the list of Business Units should be fetched
     * @Param name - name of the Business unit to be fetched
     * @return List<BusinessUnit>
     *      list of business unit entities in the given tenant namespace
     */
    public BusinessUnit getBusinessUnit(String tenantName, String name);

    /**
     * This method will be used to get the list of Business Unit entities by tenant name
     *
     * @param tenantName - tenant name from where the list of Business Units should be fetched
     *
     * @return List<BusinessUnit>
     *      list of business unit entities in the given tenant namespace
     */
    public List<BusinessUnit> getBusinessUnit(String tenantName);
}
