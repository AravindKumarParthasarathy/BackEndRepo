package com.msciq.storage.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.msciq.storage.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends DatastoreRepository<Department, String> {
    Department findByCode(String name);
    Department removeByCode(String name);

}
