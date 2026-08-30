package com.clerk.register.security;

import com.clerk.register.models.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record ClerkUserPrincipal(User user, String roleClaimed) implements UserDetails {

    public ClerkUserPrincipal {
        if (roleClaimed == null) {
            roleClaimed = user.getRole().name();
        }
    }

    public ClerkUserPrincipal(User user) {
        this(user, user.getRole().name());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleClaimed));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
