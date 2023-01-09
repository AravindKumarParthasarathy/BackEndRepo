package com.msciq.storage.service.impl;

import com.msciq.storage.model.PermissionObject;
import com.msciq.storage.repository.PermissionRepository;
import com.msciq.storage.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionObject> saveAllPermissions(List<PermissionObject> permissionObjects) {
        return permissionRepository.saveAll(permissionObjects);
    }

    @Override
    public List<PermissionObject> getAllPermissions() {
        return permissionRepository.findAll();
    }

}
