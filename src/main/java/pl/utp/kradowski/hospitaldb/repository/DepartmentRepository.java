package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.utp.kradowski.hospitaldb.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
