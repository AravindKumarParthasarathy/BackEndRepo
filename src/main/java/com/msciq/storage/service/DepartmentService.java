package com.msciq.storage.service;

import com.google.cloud.datastore.Datastore;
import com.msciq.storage.model.Department;
import com.msciq.storage.model.response.ResponseDTO;

import java.util.List;

public interface DepartmentService {

    /**
     * This method will be used to create a department in the given tenant namespace
     *
     * @param department   - entity which should be created in the given tenant namespace
     * @param datastore - datastore object of the tenant namespace where the department entity is created
     * @return ResponseDTO
     *      which has message and a isError flag
     *      if isError flag is true the message has the error message
     */
    public ResponseDTO saveDeptInGivenNamespace(Department department, Datastore datastore);

    /**
     * This method will be used to get department entity by tenant and department name
     *
     * @param tenantName - tenant name from where the department should be fetched
     * @param name - name of the department
     *
     * @return Department
     *      department entity from the given tenant namespace
     */
    public Department getDepartment(String tenantName, String name);

    /**
     * This method will be used to get the list of department entities by tenant name
     *
     * @param tenantName - tenant name from where the list of departments should be fetched
     *
     * @return List<Department>
     *      list of department entities from the given tenant namespace
     */
    public List<Department> getDepartment(String tenantName);
}
