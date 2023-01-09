package com.msciq.storage.repository;

import com.msciq.storage.common.FieldConstants;
import com.msciq.storage.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * This is the repository class for communicate link between server side and
 * database. This class used to perform all the role module action in database.
 * In query annotation (nativeQuery = true) the below query perform like SQL.
 * Otherwise its perform like HQL default value for nativeQuery FALSE
 * </p>
 * 
 * @author Rajkumar created on Jan 30, 2022
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public static final String GET_ALL_ROLES = "select role from Role as role where role.isActive =:status ";
	public static final String UPDATE_ROLE_STATUS_BY_ID = "update Role as role set role.isActive =:status where role.id =:roleId";
	public static final String GET_ROLE_BY_ID = "select role from Role as role where role.id =:roleId ";
	public static final String GET_ROLE_BY_NAME = "select role from Role as role where role.name =:name ";

	/**
	 * <p>
	 * This method get all the active role details from the database.
	 * </p>
	 * 
	 * @param status - state of the role as true or false
	 * @return List<Role> - List of Role Entity
	 */
	@Query(value = GET_ALL_ROLES)
	public List<Role> getAllRoles(@Param(FieldConstants.STATUS) boolean status);

//	@Query(value = GET_ROLE_BY_ID)
//	public Role getRoleById(@Param(FieldConstants.ROLE_ID) Long roleId);


}
