package pl.utp.kradowski.hospitaldb.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HospitaldbUserDetails implements UserDetails {
    private HospitalDBUser hospitalDBUser;

    public HospitaldbUserDetails(HospitalDBUser hospitalDBUser) {
        this.hospitalDBUser = hospitalDBUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

        UserRole role = null;

        if (hospitalDBUser !=null) {
            role = hospitalDBUser.getRole();
        }

        if(role!=null){
            grantedAuthorities.add(new AuthorityImpl(role));
        }

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return hospitalDBUser.getPassword();
    }

    @Override
    public String getUsername() {
        if (this.hospitalDBUser == null)
            return null;
        return this.hospitalDBUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.hospitalDBUser.isAccountNotExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.hospitalDBUser.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.hospitalDBUser.isPasswordNotExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.hospitalDBUser.isEnabled();
    }
}
