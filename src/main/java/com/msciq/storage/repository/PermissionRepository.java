package com.msciq.storage.repository;

import com.msciq.storage.model.PermissionObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionObject, Long> {

    public static final String GET_PERMISSION_BY_ID = "select permission from PermissionObject as permission where permission.id =:permissionId ";

    @Query(value = GET_PERMISSION_BY_ID)
    public PermissionObject getPermissionById(@Param("permissionId") long permissionId);
}
