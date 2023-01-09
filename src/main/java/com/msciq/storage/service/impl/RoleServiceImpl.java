package com.msciq.storage.service.impl;

import com.msciq.storage.model.Role;
import com.msciq.storage.repository.RoleRepository;
import com.msciq.storage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * This service class contain all the business logic for role module and perform
 * all the role operation here.
 * </p>
 * 
 * @author Rajkumar created on Jun 30, 2022
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;


	/**
	 * {@inheritDoc}
	 */
	public List<Role> addRole(List<Role> roles) {
		return roleRepository.saveAll(roles);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Role> getAllRoles() {
		return roleRepository.getAllRoles(Boolean.TRUE);
	}

}
