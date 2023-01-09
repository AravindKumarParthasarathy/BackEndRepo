package com.msciq.storage.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@Slf4j
public class DomainGrantedAuthority implements GrantedAuthority {

    private final String  entityId;
    private final Actions actions;

    @Override
    public String getAuthority() {
        log.info(" get authority "+entityId+":"+actions);
        return  entityId +
                ":" +
                actions;
    }
}