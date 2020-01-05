package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;

@Service
public class HospitalDBUserService {
    HospitalDBUserRepository dbUserRepository;
    HospitalEmployeeRepository employeeRepository;
    PasswordEncoder passEncoder;

    @Autowired
    public HospitalDBUserService(){

    }
}
