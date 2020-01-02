package pl.utp.kradowski.hospitaldb.security;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityImpl implements GrantedAuthority {
    private UserRole role;

    public AuthorityImpl(UserRole role){
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
