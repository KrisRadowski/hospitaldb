package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.utp.kradowski.hospitaldb.entity.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
