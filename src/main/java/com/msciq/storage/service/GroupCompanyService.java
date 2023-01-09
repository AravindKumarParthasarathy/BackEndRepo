package com.msciq.storage.service;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.GroupCompany;
import com.msciq.storage.model.response.ResponseDTO;

import java.util.List;

public interface GroupCompanyService {

    /**
     * This method will be used to create a group company in the given tenant namespace
     *
     * @param groupCompany   - entity which should be created in the given tenant namespace
     * @param tenantDatastore - datastore object of the tenant namespace where the group company entity is created
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    public ResponseDTO saveGroupCompanyInGivenNamespace(GroupCompany groupCompany, Datastore tenantDatastore);

    /**
     * This method will be used to get the list of group company entities by tenant name
     *
     * @param tenantName - tenant name from where the list of group companies should be fetched
     *
     * @return List<GroupCompany>
     *      list of group company entities from the given tenant namespace
     */
    public List<GroupCompany> getAllGroupCompaniesFromTenant(String tenantName);

    /**
     * This method will be used to get the group company entity by tenant name
     *
     * @param tenant - tenant name from where the group company information should be fetched
     * @param groupCompanyName - name of the group company which should be fetched from the given tenant namepsace
     *
     * @return GroupCompany
     *      entity which is fetched from the given tenant namespace
     */
    public GroupCompany getGroupCompanyFromTenantByName(String tenant, String groupCompanyName);

    /**
     * This method will be used to update the group company entity in organization namespace
     *
     * @param organizationName - tenant name from where the group company information should be fetched
     * @param groupCompanyName - name of the group company which should be updated
     * @param groupCompany - entity which should be updated in the given tenant namespace
     *
     * @return ResponseDTO
     *      which has message and an error flag
     *      if error flag is true the message has the error message
     */
    ResponseDTO updateGroupCompanyByCompanyName(String organizationName, String groupCompanyName, GroupCompany groupCompany);
}
