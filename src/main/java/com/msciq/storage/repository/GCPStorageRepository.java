package com.msciq.storage.repository;

import com.msciq.storage.model.Company;
import com.msciq.storage.model.Department;
import com.msciq.storage.model.User;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GCPStorageRepository extends DatastoreRepository {
    //List<FPDetails> findAllByUpdatedBy(String updatedBy);

}
