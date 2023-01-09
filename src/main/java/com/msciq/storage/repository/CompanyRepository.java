package com.msciq.storage.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.msciq.storage.model.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends DatastoreRepository<Company, String> {
    Company findByName(String name);
    Company removeByName(String name);
}
