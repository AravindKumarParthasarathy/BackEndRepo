package com.msciq.storage.service.impl;

import com.msciq.storage.security.Actions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.msciq.storage.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManagementServiceImpl implements UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public void setTokenClaims(String emailId, Map<String, Map<String, Set<Actions>>> requestedPermissions) throws FirebaseAuthException {
        var claims = toUserClaims(requestedPermissions);
        var userEmail = firebaseAuth.getUserByEmail(emailId);
        firebaseAuth.setCustomUserClaims(userEmail.getUid(), claims);
    }

    private Map<String, Object> toUserClaims(Map<String, Map<String, Set<Actions>>> requestedPermissions) {
        var customClaims = new ArrayList<String>();

        requestedPermissions.forEach((entityType, entityTypePermissions) ->
                entityTypePermissions.forEach((entityId, permissions) ->
                        permissions.forEach(permission ->
                                customClaims.add(generateClaim(entityType,entityId, permission))
                        )));

        return Map.of("user_roles", customClaims);
    }

    private String generateClaim( String entityType,String entityId, Actions actions) {
        return  entityType+":"+ entityId +
                ":" +
                actions;
    }
}