package com.msciq.storage.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.msciq.storage.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends DatastoreRepository<User, Long> {
    List<User> findAll();
}
