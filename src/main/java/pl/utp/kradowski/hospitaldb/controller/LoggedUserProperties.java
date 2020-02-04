package pl.utp.kradowski.hospitaldb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;

public class LoggedUserProperties {

    public String currentUsersGroup() {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken))
            return auth.getName();
        else return "";*/

        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println(new ArrayList(grantedAuthorities).get(0));
        return new ArrayList(grantedAuthorities).get(0).toString();
    }
}
