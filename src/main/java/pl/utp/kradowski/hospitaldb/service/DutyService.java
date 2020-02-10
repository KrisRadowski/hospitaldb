package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.repository.DutyRepository;

@Service
public class DutyService {
    DutyRepository dutyRepository;

    public DutyService(){};

    @Autowired
    public void setDutyRepository(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    public void addDuty(Duty d){
        dutyRepository.save(d);
    }
}
