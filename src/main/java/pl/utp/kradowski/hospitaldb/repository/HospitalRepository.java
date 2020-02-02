package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.Hospital;

import java.util.List;


public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    Hospital findByHospitalName(String name);
    @Query("SELECT h.hospitalName FROM Hospital h")
    List<String> listHospitalNames();
}
