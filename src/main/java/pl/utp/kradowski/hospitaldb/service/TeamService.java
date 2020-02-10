package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.entity.Team;
import pl.utp.kradowski.hospitaldb.repository.TeamRepository;

@Service
public class TeamService {
    TeamRepository teamRepository;

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(Team t){
        teamRepository.save(t);
    }

    public void deleteTeam(Team t) {teamRepository.delete(t);}
}
