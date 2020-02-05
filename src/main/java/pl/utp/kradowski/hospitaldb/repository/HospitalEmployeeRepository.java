package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;

import java.util.List;

public interface HospitalEmployeeRepository extends JpaRepository<HospitalEmployee,Long> {

    @Query("SELECT concat(he.firstName,' ',he.lastName) FROM HospitalEmployee he")
    List<String> listEmployees();

    @Query(value = "SELECT concat(he.first_name,' ',he.last_name) FROM hospital_employee he,HospitalDBUser u " +
            "WHERE  u.user_id = he.user_id AND u.login not like ?1",nativeQuery = true)
    List<String> listEmployees(String userToExclude);
}
