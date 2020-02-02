package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.entity.Hospital;
import pl.utp.kradowski.hospitaldb.repository.HospitalRepository;

@Service
public class HospitalService {
    HospitalRepository hospitalRepository;

    public HospitalService(){};

    @Autowired
    public void setHospitalRepository(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public void addHospital(Hospital h){
        hospitalRepository.save(h);
    }
}
