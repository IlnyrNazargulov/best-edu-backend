package ru.ilnyrdiplom.bestedu.web.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class JwtAuthentication implements Authentication {
    private final Collection<? extends GrantedAuthority> authorities;
    private final String subject;
    private final Map<String, Object> claims;

    @Override
    public Object getCredentials() {
        return subject;
    }

    @Override
    public Object getDetails() {
        return claims;
    }

    @Override
    public Object getPrincipal() {
        return subject;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("impossible to change auth");
    }

    @Override
    public String getName() {
        return subject;
    }
}
