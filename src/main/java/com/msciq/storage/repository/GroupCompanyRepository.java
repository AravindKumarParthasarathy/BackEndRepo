package com.msciq.storage.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.msciq.storage.model.GroupCompany;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCompanyRepository extends DatastoreRepository<GroupCompany, String> {
}
