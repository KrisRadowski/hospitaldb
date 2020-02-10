package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "duty_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="dept_id",nullable = false)
    private Department dept;
    @Column(nullable = false)
    private Date startTime,endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="team_id",nullable = false)
    private Team team;

    @Column(nullable = false)
    private DutyType dutyType;

    @Column(nullable = false)
    private boolean confirmed;

    public Duty() {

    }

    public Duty(Department dept, Date startTime, Date endTime, Team team) {
        this.dept = dept;
        this.startTime = startTime;
        this.endTime = endTime;
        this.team = team;
        this.confirmed =false;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.confirmed = isConfirmed;
    }

    public DutyType getDutyType() {
        return dutyType;
    }

    public void setDutyType(DutyType dutyType) {
        this.dutyType = dutyType;
    }

    public Long getId() {
        return id;
    }
}
