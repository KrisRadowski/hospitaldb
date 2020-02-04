package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;

public interface HospitalEmployeeRepository extends JpaRepository<HospitalEmployee,Long> {

}
