package com.msciq.storage.service;



import com.msciq.storage.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;


/**
 * <p>
 * This an interface class for role module you can implemented this class in any
 * class.
 * </p>
 * 
 * @author Rajkumar created on Jun 30, 2022
 */
public interface RoleService {

	/**
	 * <p>
	 * This method used to add a role detail.
	 * </p>
	 * 
	 * @param roles - role to be added
	 * @return Role - added role information
	 */
	List<Role> addRole(List<Role> roles);

	/**
	 * <p>
	 * This method used to retrieve all the active role.
	 * </p>
	 * 
	 * @return List<Role> - list of Role Entity
	 */
	List<Role> getAllRoles();


}
