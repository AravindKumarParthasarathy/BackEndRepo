package com.msciq.storage.controller;

import com.msciq.storage.security.Actions;
import com.google.firebase.auth.FirebaseAuthException;
import com.msciq.storage.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserManagementService userManagementService;

    @Secured("ROLE_ANONYMOUS")
    @PostMapping(path = "/user-claims/{email}")
    public void setUserClaims(
            @PathVariable String email,
            @RequestBody Map<String, Map<String, Set<Actions>>> requestedClaims
    ) throws FirebaseAuthException {
        userManagementService.setTokenClaims(email, requestedClaims);
    }
}
