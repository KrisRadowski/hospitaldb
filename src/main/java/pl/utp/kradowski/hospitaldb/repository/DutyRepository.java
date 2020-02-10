package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.Duty;

import java.util.Date;
import java.util.List;

public interface DutyRepository extends JpaRepository<Duty,Long> {

    @Query(value = "SELECT * From duty d where team_id!=?1",nativeQuery = true)
    List<Duty> getAllDutiesToReplace(long teamId);

    @Query(value = "Select * from duty d where start_time=?1 and end_time=?2 and team_id=?3 and dept_id=?4",nativeQuery = true)
    Duty getDuty(Date startTime,Date endTime,long teamID,long deptID);

    @Query(value = "SELECT * From duty d where team_id=?1",nativeQuery = true)
    List<Duty> getAllDuties(Long id);

    @Query(value = "SELECT case when count(d)>0 then true else false end from duty d where d.confirmed=false and d.team_id=(" +
            "select team.team_id from team where team.employee_id=(" +
            "select he.employee_id from hospital_employee he where he.user_id=(" +
            "select u.user_id from hospitaldbuser u where u.login=?1)))",nativeQuery = true)
    boolean unconfirmedDuties(String name);

    @Query(value = "Select * from Duty d where d.confirmed=false and d.team_id=(" +
            "select team.team_id from team where team.employee_id=(" +
            "select he.employee_id from hospital_employee he where he.user_id=(" +
            "select u.user_id from hospitaldbuser u where u.login=?1))) order by start_time limit 1",nativeQuery = true)
    Duty getUnconfirmedDuty(String name);

    @Query(value="Select * from duty d where",nativeQuery = true)
    Duty getOtherSideDuty(Duty dutyToConfirmation);
}
