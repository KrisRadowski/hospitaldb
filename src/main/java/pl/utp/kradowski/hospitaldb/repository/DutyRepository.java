package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.entity.Team;

import java.util.List;

public interface DutyRepository extends JpaRepository<Duty,Long> {

    @Query(value = "SELECT * From duty d where team_id!=?1",nativeQuery = true)
    List<Duty> getAllDuties(long teamId);
}
