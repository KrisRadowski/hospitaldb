package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;

public interface HospitalEmployeeRepository extends JpaRepository<HospitalDBUser,Long> {
}
