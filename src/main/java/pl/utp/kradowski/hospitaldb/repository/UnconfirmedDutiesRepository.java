package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.utp.kradowski.hospitaldb.entity.UnconfirmedDuties;

public interface UnconfirmedDutiesRepository extends JpaRepository<UnconfirmedDuties,Long> {
    UnconfirmedDuties getByDuty2(long dutyId);

}
