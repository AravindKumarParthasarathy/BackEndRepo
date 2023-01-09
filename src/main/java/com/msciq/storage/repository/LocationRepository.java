package com.msciq.storage.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.msciq.storage.model.Department;
import com.msciq.storage.model.Location;
import com.msciq.storage.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends DatastoreRepository<Location, String> {

    List<Location> findAll();

}
