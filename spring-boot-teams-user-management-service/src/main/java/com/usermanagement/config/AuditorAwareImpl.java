package com.usermanagement.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// Auditor Aware tells the system who is the current user that is taking action
// is used for Annotations: @CreatedBy or @LastModifiedBy --> They save in our case the id of the user as string
// if there is no current user, then this is null (e.g. when our Initializer.java creates users or teams)
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        //  return the "subject" from the security context holder (it's the id of the user as string in our case)
        return Optional.ofNullable(authentication.getName());
    }

}
