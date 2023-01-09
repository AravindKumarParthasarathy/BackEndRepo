package com.msciq.storage.config;

import com.msciq.storage.security.DomainGrantedAuthority;
import com.msciq.storage.security.Actions;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                Optional.ofNullable(jwt.getClaimAsStringList("user_roles"))
                        .stream()
                        .flatMap(Collection::stream)
                        .flatMap(claim -> {
                            var parts = claim.split(":", 3);

                            String entityId;
                            Actions actions;

                            try {
                                entityId =parts[1];
                                actions = Actions.valueOf(parts[2]);
                            } catch (IllegalArgumentException e) {
                                return Stream.empty();
                            }

                            return Stream.of(new DomainGrantedAuthority(entityId, actions));
                        })
                        .collect(Collectors.toList()));

        return converter;
    }
}