package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "team_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee_id")
    private HospitalEmployee teamLeader;
    private Long teamEmployee1;
    private Long teamEmployee2;

    public Team() {

    }

    public Team(HospitalEmployee teamLeader, Long teamEmployee1, Long teamEmployee2) {
        this.teamLeader = teamLeader;
        this.teamEmployee1 = teamEmployee1;
        this.teamEmployee2 = teamEmployee2;
    }

    public HospitalEmployee getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(HospitalEmployee teamLeader) {
        this.teamLeader = teamLeader;
    }

    public Long getTeamEmployee1() {
        return teamEmployee1;
    }

    public void setTeamEmployee1(Long teamEmployee1) {
        this.teamEmployee1 = teamEmployee1;
    }

    public Long getTeamEmployee2() {
        return teamEmployee2;
    }

    public void setTeamEmployee2(Long teamEmployee2) {
        this.teamEmployee2 = teamEmployee2;
    }

    public Long getId() {
        return id;
    }
}
