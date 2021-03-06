package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;
import pl.utp.kradowski.hospitaldb.entity.Position;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;

@Service
public class HospitalDBUserService {
    HospitalDBUserRepository dbUserRepository;
    HospitalEmployeeRepository employeeRepository;
    PasswordEncoder passEncoder;

    @Autowired
    public HospitalDBUserService(PasswordEncoder passwordEncoder){
        this.passEncoder = passwordEncoder;
    }

    @Autowired
    public void setDbUserRepository(HospitalDBUserRepository dbUserRepository) {
        this.dbUserRepository = dbUserRepository;
    }

    @Autowired
    public void setEmployeeRepository(HospitalEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addUser(String firstName,String lastName,HospitalDBUser user,Position p){
        saveToUserDatabase(user);
        HospitalEmployee employee = new HospitalEmployee(firstName,lastName,user,p);
        employeeRepository.save(employee);
    }

    private void saveToUserDatabase(HospitalDBUser user) {
        String passwordHash = passEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        user.setEnabled(true);
        user.setNotLocked(true);
        user.setAccountNotExpired(true);
        user.setPasswordNotExpired(true);
        dbUserRepository.save(user);
    }

    public void addUser(HospitalDBUser user) {
        saveToUserDatabase(user);
    }
}
