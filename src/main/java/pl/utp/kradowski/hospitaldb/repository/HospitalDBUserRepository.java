package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;

@Repository
public interface HospitalDBUserRepository extends CrudRepository<HospitalDBUser,Long> {
    HospitalDBUser findById(long id);
    HospitalDBUser findByHospitalDBUsername(String userName);
}
