package com.msciq.storage.service.impl;

import com.google.cloud.datastore.*;
import com.msciq.storage.model.Department;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.repository.DepartmentRepository;
import com.msciq.storage.service.DepartmentService;
import com.msciq.storage.service.GCPStorageService;
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
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private GCPStorageService gcpStorageService;

    public ResponseDTO saveDeptInGivenNamespace(Department department, Datastore datastore) {
        try {
            Key taskKey = datastore.newKeyFactory()
                    .setKind("Department")
                    .newKey(department.getName() + UUID.randomUUID());
            Entity newEntity = Entity.newBuilder(taskKey)
                    .set("code", department.getCode())
                    .set("name", department.getName())
                    .set("activeFromDate", department.getActiveFromDate() != null
                            ? String.valueOf(department.getActiveFromDate())
                            : String.valueOf(Constants.DEFAULT_ACTIVE_FROM_DATE))
                    .set("isActive", true)
                    .set("createdBy", "admin") // TODO: Take user email from the token and set as createdBy and updatedBy
                    .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                    .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                    .build();
            datastore.put(newEntity);
            return ResponseDTO.builder()
                    .message("Department " + department.getName() + " created successfully")
                    .isError(false)
                    .build();
        } catch (Exception e) {
            return ResponseDTO.builder()
                    .message(ErrorMessage.DEPARTMENT_ERROR + e.getMessage())
                    .isError(true)
                    .build();
        }
    }


    @Override
    public Department getDepartment(String tenantName, String name) {
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Department")
                .setFilter(StructuredQuery.PropertyFilter.eq("name", name))
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            return buildDepartmentEntity(resultEntity);
        }
        return null;
    }

    @Override
    public List<Department> getDepartment(String tenantName) {
        List<Department> departmentList = new ArrayList<>();
        String tenantNamespace = Constants.ORGANIZATION_PREFIX + tenantName.toLowerCase();
        Datastore tenantDatastore = gcpStorageService.createOrGetNamespace(tenantNamespace);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("Department")
                .build();
        QueryResults<Entity> results = tenantDatastore.run(query);
        while (results.hasNext()) {
            Entity resultEntity = results.next();
            departmentList.add(buildDepartmentEntity(resultEntity));
        }
        return departmentList;
    }

    private static Department buildDepartmentEntity(Entity resultEntity) {
        return Department.builder()
                .activeFromDate(LocalDateTime.parse(resultEntity.getString("activeFromDate")))
                .code(resultEntity.getString("code"))
                .name(resultEntity.getString("name"))
                .createdDt(LocalDateTime.parse(resultEntity.getString("createdDt")))
                .isActive(resultEntity.getBoolean("isActive"))
                .inactiveDate(LocalDateTime.parse(resultEntity.getString("inactiveDate")))
                .createdBy(resultEntity.getString("createdBy"))
                .lastUpdatedBy(resultEntity.getString("lastUpdatedBy"))
                .lastUpdatedDt(LocalDateTime.parse(resultEntity.getString("lastUpdatedDt")))
                .id(resultEntity.getKey().getName())
                .build();
    }

    public Department getDepartment(String departmentCode, Datastore datastore) {
        return departmentRepository.findByCode(departmentCode);
    }

    public Department updateDepartment(Department department) {
        return (Department) departmentRepository.save(department);
    }

    public Department removeDepartment(String code) {
        return departmentRepository.removeByCode(code);
    }

}
