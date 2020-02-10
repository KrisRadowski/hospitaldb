package pl.utp.kradowski.hospitaldb.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.utp.kradowski.hospitaldb.entity.*;
import pl.utp.kradowski.hospitaldb.repository.*;

import java.util.ArrayList;
import java.util.Collection;

public class LoggedUserProperties {

    private DutyRepository dutyRepository;
    private HospitalEmployeeRepository repository;
    private TeamRepository teamRepository;
    public LoggedUserProperties(){

    }

    public LoggedUserProperties(HospitalEmployeeRepository r){
        this.repository = r;
    }

    public LoggedUserProperties(HospitalEmployeeRepository r,TeamRepository t){
        this.repository = r;
        this.teamRepository = t;
    }

    public LoggedUserProperties(HospitalEmployeeRepository r, TeamRepository t, DutyRepository d){
        this.repository = r;
        this.teamRepository = t;
        this.dutyRepository = d;
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

    public Team getUsersTeam() {
        if(userBelongsInTeam()){
            return teamRepository.getUsersTeam(auth.getName());
        } else return null;
    }

    public boolean userHasUnconfirmedDuties() {
        if(userIsTeamLeader()){
            return dutyRepository.hasUnconfirmedDuties(auth.getName());
        } else return false;
    }

    public long unconfirmedDuty(){
        if(userIsTeamLeader()){
            return dutyRepository.getUnconfirmedDutyId(auth.getName());
        } else return 0;
    }
}
