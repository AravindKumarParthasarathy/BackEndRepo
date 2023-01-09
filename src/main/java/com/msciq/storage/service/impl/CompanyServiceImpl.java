package com.msciq.storage.service.impl;

import com.google.cloud.datastore.*;
import com.msciq.storage.model.Company;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.repository.CompanyRepository;
import com.msciq.storage.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private GCPStorageService gcpStorageService;

    public ResponseDTO saveCompanyInGivenNamespace(Company company, Datastore datastore) {
        try {
        Key taskKey = datastore.newKeyFactory()
                .setKind("Company")
                .newKey("company_" + UUID.randomUUID());
        Entity newEntity = Entity.newBuilder(taskKey)
                .set("groupCompanyCode", company.getGroupCompanyCode())
                .set("name", company.getName())
                .set("currency", company.getCurrency())
                .set("isActive", true)
                .set("activeFromDate", company.getActiveFromDate() != null
                        ? String.valueOf(company.getActiveFromDate())
                        : String.valueOf(Constants.DEFAULT_ACTIVE_FROM_DATE))
                .set("fiscalYear", company.getFiscalYear())
                .set("inActiveDate", "")
                .set("createdBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .set("lastUpdatedBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .build();
        datastore.put(newEntity);
        return ResponseDTO.builder()
                .message("Company " + company.getName() + " created successfully")
                .isError(false)
                .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message("Error while creating company - " + e.getMessage())
                    .isError(true)
                    .build();
        }
    }

    @Override
    public Company getCompany(String tenantName, String name) {
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase().replaceAll("\\s", "");
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Company")
                .setFilter(StructuredQuery.PropertyFilter.eq("name", name))
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            return buildCompanyEntity(resultEntity);
        }
        return null;
    }

    @Override
    public List<Company> getCompany(String tenantName) {
        List<Company> companyList = new ArrayList<>();
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Company")
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            companyList.add(buildCompanyEntity(resultEntity));
        }
        return companyList;
    }

    private static Company buildCompanyEntity(Entity resultEntity) {
        return Company.builder()
                .activeFromDate(LocalDateTime.parse(resultEntity.getString("activeFromDate")))
                .groupCompanyCode(resultEntity.getString("groupCompanyCode"))
                .currency(resultEntity.getString("currency"))
                .name(resultEntity.getString("name"))
                .createdDt(LocalDateTime.parse(resultEntity.getString("createdDt")))
                .fiscalYear(resultEntity.getString("fiscalYear"))
                .isActive(resultEntity.getBoolean("isActive"))
                .inActiveDate(!Validator.isEmptyString(resultEntity.getString("inActiveDate")) ? LocalDateTime.parse(resultEntity.getString("inActiveDate")) : null)
                .createdBy(resultEntity.getString("createdBy"))
                .lastUpdatedBy(resultEntity.getString("lastUpdatedBy"))
                .lastUpdatedDt(LocalDateTime.parse(resultEntity.getString("lastUpdatedDt")))
                .id(resultEntity.getKey().getName())
                .build();
    }

    public Company updateCompany(Company company) {
        return (Company) companyRepository.save(company);
    }
    public Company removeCompany(String name) {
        return companyRepository.removeByName(name);
    }

}
