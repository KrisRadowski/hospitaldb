package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;

import java.util.List;

public interface HospitalEmployeeRepository extends JpaRepository<HospitalEmployee,Long> {

    @Query("SELECT concat(he.firstName,' ',he.lastName,' - ',he.position) FROM HospitalEmployee he")
    List<String> listEmployees();

    @Query("SELECT concat(he.firstName,' ',he.lastName,' - ',he.position) FROM HospitalEmployee he" +
            " INNER JOIN HospitalDBUser u ON u.id = he.id " +
            "WHERE u.login not like ?1")
    List<String> listEmployees(String userToExclude);
}
