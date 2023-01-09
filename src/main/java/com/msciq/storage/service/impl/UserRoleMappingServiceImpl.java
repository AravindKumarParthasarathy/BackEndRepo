package com.msciq.storage.service.impl;

import com.msciq.storage.model.UserRoleMapping;
import com.msciq.storage.repository.UserRoleMappingRepository;
import com.msciq.storage.service.UserRoleMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleMappingServiceImpl implements UserRoleMappingService {
    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;
    @Override
    public  List<UserRoleMapping> saveAllUserRoleMappings(List<UserRoleMapping> userRoleMappings) {
        return userRoleMappingRepository.saveAll(userRoleMappings);
    }

    @Override
    public List<UserRoleMapping> getUserRoleMappings() {
        return userRoleMappingRepository.findAll();
    }
}
