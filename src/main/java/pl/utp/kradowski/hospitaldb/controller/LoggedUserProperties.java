package pl.utp.kradowski.hospitaldb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;
import pl.utp.kradowski.hospitaldb.entity.Position;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;

import java.util.ArrayList;
import java.util.Collection;

public class LoggedUserProperties {

    private HospitalEmployeeRepository repository;
    public LoggedUserProperties(){

    }

    public LoggedUserProperties(HospitalEmployeeRepository r){
        this.repository = r;
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    public String currentUsersGroup() {
        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return new ArrayList(grantedAuthorities).get(0).toString();
    }

    public String currentUser(){
        if(!(auth instanceof AnonymousAuthenticationToken))
            return auth.getName();
        else return "";
    }

    public HospitalEmployee currentUserAsEmployeeEntity(){
        if(!(auth instanceof AnonymousAuthenticationToken))
            return repository.findByLogin(auth.getName());
        else return null;
    }

    public Integer currentUsersPosition(){
        if(!(auth instanceof AnonymousAuthenticationToken))
            return repository.getEmployeePositionAsInteger(auth.getName());
        else return null;
    }

    public boolean userIsTeamLeader(){
        if(!(auth instanceof AnonymousAuthenticationToken))
            return repository.employeeIsTeamLeader(auth.getName());
        else return false;
    }

    public boolean userBelongsInTeam() {
        if(!(auth instanceof AnonymousAuthenticationToken))
            return repository.employeeIsInTeam(auth.getName());
        else return false;
    }
}
