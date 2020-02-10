package pl.utp.kradowski.hospitaldb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;
import pl.utp.kradowski.hospitaldb.entity.Position;
import pl.utp.kradowski.hospitaldb.entity.Team;

import java.util.List;

public interface HospitalEmployeeRepository extends JpaRepository<HospitalEmployee,Long> {

    @Query("SELECT concat(he.firstName,' ',he.lastName) FROM HospitalEmployee he")
    List<String> listEmployees();

    @Query(value = "SELECT concat(he.first_name,' ',he.last_name,' (',he.employee_id,')') FROM hospital_employee he,HospitalDBUser u " +
            "WHERE  u.user_id = he.user_id AND u.login not like ?1",nativeQuery = true)
    List<String> listEmployees(String userToExclude);

    @Query(value = "SELECT concat(he.first_name,' ',he.last_name,' (',he.employee_id,')') FROM hospital_employee he,HospitalDBUser u,team t " +
            "WHERE  u.user_id = he.user_id AND u.login not like ?1 AND he.position >= ?2 " +
            "and he.employee_id!=t.employee_id and he.employee_id!= t.team_employee1 and he.employee_id!=t.team_employee2",nativeQuery = true)
    List<String> listEmployees(String userToExclude, int maximumPosition);

    @Query(value = "SELECT he.position FROM hospital_employee he, hospitaldbuser u " +
            "WHERE u.user_id=he.user_id AND u.login like ?1",nativeQuery = true)
    int getEmployeePositionAsInteger(String user);

    @Query(value = "SELECT * FROM hospital_employee he, hospitaldbuser u " +
            "WHERE u.user_id=he.user_id AND u.login like ?1",nativeQuery = true)
    HospitalEmployee findByLogin(String user);

    @Query(value = "SELECT CASE when count(d)>0 then true else false end from team d " +
            "where d.employee_id = (SELECT he.employee_id " +
            "FROM hospital_employee he " +
            "INNER JOIN hospitaldbuser h on he.user_id = h.user_id " +
            "where h.login like ?1)",nativeQuery = true)
    boolean employeeIsTeamLeader(String name);

    @Query(value ="SELECT CASE when count(d)>0 then true else false end from team d "+
            "where d.employee_id = (SELECT he.employee_id "+
            "FROM hospital_employee he " +
            "INNER JOIN hospitaldbuser h on he.user_id = h.user_id " +
            "where h.login like ?1) " +
            "or d.team_employee1 = (SELECT he.employee_id " +
            "FROM hospital_employee he " +
            "INNER JOIN hospitaldbuser h on he.user_id = h.user_id " +
            "where h.login like ?1) " +
            "or d.team_employee2 = (SELECT he.employee_id " +
            "FROM hospital_employee he " +
            "INNER JOIN hospitaldbuser h on he.user_id = h.user_id " +
            "where h.login like ?1)",nativeQuery = true)
    boolean employeeIsInTeam(String name);

}
