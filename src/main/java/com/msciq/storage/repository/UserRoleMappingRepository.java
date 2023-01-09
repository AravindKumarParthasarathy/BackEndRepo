package com.msciq.storage.repository;

import com.msciq.storage.model.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, Long>{
}
