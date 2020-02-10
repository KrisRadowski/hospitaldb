package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.utp.kradowski.hospitaldb.entity.Duty;

import java.util.Date;
import java.util.List;

public interface DutyRepository extends JpaRepository<Duty,Long> {

    @Query(value = "SELECT * From duty d where team_id!=?1",nativeQuery = true)
    List<Duty> getAllDutiesToReplace(long teamId);

    @Query(value = "Select * from duty d where start_time=?1 and end_time=?2 and team_id=?3 and dept_id=?4",nativeQuery = true)
    Duty getDuty(Date startTime,Date endTime,long teamID,long deptID);

    Duty getDutyById(long Id);

    @Query(value = "SELECT * From duty d where team_id=?1",nativeQuery = true)
    List<Duty> getAllDuties(Long id);

    @Query(value = "SELECT case when count(d)>0 then true else false end from duty d,unconfirmed_duties ud where d.confirmed=false and d.team_id=(" +
            "select team.team_id from team where team.employee_id=(" +
            "select he.employee_id from hospital_employee he where he.user_id=(" +
            "select u.user_id from hospitaldbuser u where u.login=?1))) and d.duty_id=ud.duty2 ",nativeQuery = true)
    boolean hasUnconfirmedDuties(String name);

    @Query(value = "SELECT d.duty_id from duty d,unconfirmed_duties ud where d.confirmed=false and d.team_id=(" +
            "select team.team_id from team where team.employee_id=(" +
            "select he.employee_id from hospital_employee he where he.user_id=(" +
            "select u.user_id from hospitaldbuser u where u.login=?1))) and d.duty_id=ud.duty2 order by d.duty_id limit 1",nativeQuery = true)
    Long getUnconfirmedDutyId(String name);

    @Transactional
    @Modifying
    @Query("Update Duty d set d.confirmed=true where d.id = ?1")
    void setDutyAsConfirmed(long dutyId);

    @Query(value = "Select * from duty d where d.team_id=(" +
            "select team.team_id from team where team.employee_id=?1 or team.team_employee1=?1 or team.team_employee2=?1)",nativeQuery = true)
    List<Duty> getAllDutiesToDo(long employeeID);
}
