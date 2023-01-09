package com.msciq.storage.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.msciq.storage.model.BusinessUnit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessUnitRepository extends DatastoreRepository<BusinessUnit, String> {
    List<BusinessUnit> findAll();
}
