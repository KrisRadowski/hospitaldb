package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "team_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private HospitalEmployee teamLeader;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private HospitalEmployee teamEmployee1;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private HospitalEmployee teamEmployee2;

    public Team() {

    }

    public Team(HospitalEmployee teamLeader, HospitalEmployee teamEmployee1, HospitalEmployee teamEmployee2) {
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

    public HospitalEmployee getTeamEmployee1() {
        return teamEmployee1;
    }

    public void setTeamEmployee1(HospitalEmployee teamEmployee1) {
        this.teamEmployee1 = teamEmployee1;
    }

    public HospitalEmployee getTeamEmployee2() {
        return teamEmployee2;
    }

    public void setTeamEmployee2(HospitalEmployee teamEmployee2) {
        this.teamEmployee2 = teamEmployee2;
    }
}
