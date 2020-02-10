package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
    @Query(value = "select * from team t where t.employee_id=(select he.employee_id from hospital_employee he " +
            "inner join hospitaldbuser h on he.user_id = h.user_id " +
            "where h.login like ?1)",nativeQuery = true)
    Team getUsersTeam(String name);
}
