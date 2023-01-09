package com.msciq.storage.repository;

import com.msciq.storage.common.FieldConstants;
import com.msciq.storage.model.RolePermissionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.msciq.storage.common.QueryConstants.GET_PERMISSION_BY_ROLE_NAME;

@Repository
public interface RolePermissionMappingRepository extends JpaRepository<RolePermissionMapping, Long> {

    @Query(countQuery = GET_PERMISSION_BY_ROLE_NAME)
    public List<RolePermissionMapping> getAllRolePermissionMappingByRoleName(@Param("roleName") String roleName);
}
