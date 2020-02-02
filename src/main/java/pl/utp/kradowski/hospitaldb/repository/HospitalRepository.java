package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.utp.kradowski.hospitaldb.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
