package com.msciq.storage.service.impl;

import com.google.cloud.datastore.*;
import com.msciq.storage.model.GroupCompany;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.repository.GCPStorageRepository;
import com.msciq.storage.service.GCPStorageService;
import com.msciq.storage.service.GroupCompanyService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupCompanyServiceImpl implements GroupCompanyService {

    @Autowired
    private GCPStorageRepository gcpStorageRepository;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private GCPStorageService gcpStorageService;

    @Override
    public ResponseDTO saveGroupCompanyInGivenNamespace(GroupCompany groupCompany, Datastore tenantDatastore) {
        try {
            Key taskKey = tenantDatastore.newKeyFactory()
                    .setKind("Group Company")
                    .newKey(Constants.GROUP_COMPANY_PREFIX + UUID.randomUUID());
            Entity groupCompanyEntity = Entity.newBuilder(taskKey)
                    .set("code", groupCompany.getCode())
                    .set("name", groupCompany.getName())
                    .set("currency", groupCompany.getCurrency())
                    .set("isActive", true)
                    .set("createdBy", "admin") // TODO: Take user email from the token and set as createdBy
                    .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                    .set("lastUpdatedBy", "admin")  // TODO: Take user email from the token and set as updatedBy
                    .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                    .build();
            tenantDatastore.put(groupCompanyEntity);
            return ResponseDTO.builder()
                    .message("Group company " + groupCompany.getName() + " created successfully")
                    .isError(false)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message(ErrorMessage.GROUP_COMPANY_ERROR + e.getMessage())
                    .isError(true)
                    .build();
        }
    }

    @Override
    public List<GroupCompany> getAllGroupCompaniesFromTenant(String tenantName) {
        List<GroupCompany> groupCompanies = new ArrayList<>();
        Datastore tenantDatastore = getTenantDatastore(tenantName);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Group Company")
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            groupCompanies.add(buildGroupCompanyEntity(resultEntity));

        }
        return groupCompanies;
    }

    private Datastore getTenantDatastore(String tenantName) {
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase().replaceAll("\\s", "_");
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);
        return tenantDatastore;
    }

    private static GroupCompany buildGroupCompanyEntity(Entity resultEntity) {
        return GroupCompany.builder()
                .code(resultEntity.getString("code"))
                .currency(resultEntity.getString("currency"))
                .name(resultEntity.getString("name"))
                .id(resultEntity.getKey().getName())
                .build();
    }

    @Override
    public GroupCompany getGroupCompanyFromTenantByName(String tenantName, String groupCompanyName) {
        Datastore tenantDatastore = getTenantDatastore(tenantName);
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Group Company")
                .setFilter(StructuredQuery.PropertyFilter.eq("name", groupCompanyName))
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            return buildGroupCompanyEntity(resultEntity);

        }
        return null;
    }

    @Override
    public ResponseDTO updateGroupCompanyByCompanyName(String tenantName, String groupCompanyName, GroupCompany groupCompany) {
        try {
            Datastore tenantDatastore = getTenantDatastore(tenantName);
            Query<Entity> query = Query.newEntityQueryBuilder()
                    .setKind("Group Company")
                    .setFilter(StructuredQuery.PropertyFilter.eq("name", groupCompanyName))
                    .build();
            QueryResults<Entity> results = tenantDatastore.run(query);
            while (results.hasNext()) {
                Entity resultEntity = results.next();
                Entity entityToBeUpdated = Entity.newBuilder(resultEntity.getKey())
                        .set("code", null != groupCompany.getCode() ? groupCompany.getCode() : resultEntity.getString("code"))
                        .set("name", null != groupCompany.getName() ? groupCompany.getName() : resultEntity.getString("name"))
                        .set("currency", null != groupCompany.getCurrency() ? groupCompany.getCurrency() : resultEntity.getString("currency"))
                        .set("lastUpdatedBy", "admin")  // TODO: Take user email from the token and set as createdBy and updatedBy
                        .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                        .build();
                tenantDatastore.update(entityToBeUpdated);
            }
            return ResponseDTO.builder()
                    .message(groupCompanyName + " updated successfully")
                    .isError(false)
                    .build();
        } catch (Exception exception) {
            return ResponseDTO.builder()
                    .message("Error while updating " + groupCompanyName + " Error is " + exception.getMessage())
                    .isError(true)
                    .build();
        }
    }
}
