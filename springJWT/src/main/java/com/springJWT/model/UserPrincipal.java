package com.springJWT.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springJWT.model.enums.UserState;

import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L; // Ensure serialization compatibility

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map the role directly to a granted authority
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement custom logic for account expiration, if necessary
        return true;  // You can make this reflect `UserState` if you want to handle expiration logic
    }

    @Override
    public boolean isAccountNonLocked() {
        // Based on the `UserState`, decide if the account is locked
        return !user.getState().equals(UserState.LOCKED);  // Example: If the user state is LOCKED, account is locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement custom logic for credential expiration, if necessary
        return true;  // You can add additional checks based on `UserState` or other fields
    }

    @Override
    public boolean isEnabled() {
        // Use `UserState` to determine if the user is enabled
        return user.getState().equals(UserState.ACTIVE);  // Example: Only ACTIVE users are enabled
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "username='" + user.getUsername() + '\'' +
                ", authorities=" + getAuthorities() +
                ", state=" + user.getState() + // Include state for debugging
                '}';
    }
}
