package com.msciq.storage.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.msciq.storage.security.Actions;

import java.util.Map;
import java.util.Set;

public interface UserManagementService {
    public void setTokenClaims(String emailId, Map<String, Map<String, Set<Actions>>> requestedPermissions) throws FirebaseAuthException;

    }