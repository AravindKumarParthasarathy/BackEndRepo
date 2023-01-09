package com.msciq.storage.service.impl;

import com.google.cloud.datastore.*;
import com.msciq.storage.model.BusinessUnit;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.repository.BusinessUnitRepository;
import com.msciq.storage.service.BusinessUnitService;
import com.msciq.storage.service.GCPStorageService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {
    @Autowired
    private BusinessUnitRepository businessUnitRepository;
    @Autowired
    private GCPStorageService gcpStorageService;
    @Override
    public ResponseDTO saveBusinessUnitInGivenNamespace(BusinessUnit businessUnit, Datastore datastore) {
        try{
        Key taskKey = datastore.newKeyFactory()
                .setKind("BusinessUnit")
                .newKey(businessUnit.getName() + UUID.randomUUID());
        Entity newEntity = Entity.newBuilder(taskKey)
                .set("groupCompanyCode", businessUnit.getGroupCompanyCode())
                .set("code", businessUnit.getCode())
                .set("name", businessUnit.getName())
                .set("isActive", true)
                .set("inactiveDate", "")
                .set("activeFromDate", businessUnit.getActiveFromDate() != null
                        ? String.valueOf(businessUnit.getActiveFromDate())
                        : String.valueOf(Constants.DEFAULT_ACTIVE_FROM_DATE))
                .set("createdBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("lastUpdatedBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .build();
        datastore.put(newEntity);
        return ResponseDTO.builder()
                .message("BusinessUnit " + businessUnit.getName() + " created successfully")
                .isError(false)
                .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error while creating businessUnit - " + e.getMessage())
                    .isError(true)
                    .build();
        }
    }

    @Override
    public BusinessUnit getBusinessUnit(String tenantName, String name) {
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("BusinessUnit")
                .setFilter(StructuredQuery.PropertyFilter.eq("name", name))
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            return buildBusinessUnitEntity(resultEntity);
        }
        return null;
    }

    @Override
    public List<BusinessUnit> getBusinessUnit(String tenantName) {
        List<BusinessUnit> businessUnitList = new ArrayList<>();
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("BusinessUnit")
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            businessUnitList.add(buildBusinessUnitEntity(resultEntity));
        }
        return businessUnitList;
    }

    private static BusinessUnit buildBusinessUnitEntity(Entity resultEntity) {
        return BusinessUnit.builder()
                .activeFromDate(LocalDateTime.parse(resultEntity.getString("activeFromDate")))
                .code(resultEntity.getString("code"))
                .name(resultEntity.getString("name"))
                .groupCompanyCode(resultEntity.getString("groupCompanyCode"))
                .isActive(resultEntity.getBoolean("isActive"))
                .inactiveDate(!Validator.isEmptyString(resultEntity.getString("inactiveDate"))
                        ? LocalDateTime.parse(resultEntity.getString("inactiveDate"))
                        : null)
                .createdBy(resultEntity.getString("createdBy"))
                .createdDt(LocalDateTime.parse(resultEntity.getString("createdDt")))
                .lastUpdatedBy(resultEntity.getString("lastUpdatedBy"))
                .lastUpdatedDt(LocalDateTime.parse(resultEntity.getString("lastUpdatedDt")))
                .id(resultEntity.getKey().getName())
                .build();
    }}
