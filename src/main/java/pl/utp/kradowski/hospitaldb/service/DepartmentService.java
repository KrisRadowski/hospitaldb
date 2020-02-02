package pl.utp.kradowski.hospitaldb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.utp.kradowski.hospitaldb.entity.Department;
import pl.utp.kradowski.hospitaldb.repository.DepartmentRepository;

@Service
public class DepartmentService {
    DepartmentRepository departmentRepository;

    public DepartmentService(){};

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void addDepartment(Department d){
        departmentRepository.save(d);
    }
}
