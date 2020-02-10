package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.entity.UnconfirmedDuties;
import pl.utp.kradowski.hospitaldb.repository.UnconfirmedDutiesRepository;

@Service
public class UnconfirmedDutiesService {
    UnconfirmedDutiesRepository repository;

    @Autowired
    public void setRepository(UnconfirmedDutiesRepository repository) {
        this.repository = repository;
    }

    public void addPair(UnconfirmedDuties unconfirmedDuties){
        repository.save(unconfirmedDuties);
    };
}
