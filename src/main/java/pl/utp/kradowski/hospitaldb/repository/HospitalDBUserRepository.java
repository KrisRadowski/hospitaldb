package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;


public interface HospitalDBUserRepository extends JpaRepository<HospitalDBUser,Long> {
    HospitalDBUser findById(long id);
    HospitalDBUser findByLogin(String login);
}
