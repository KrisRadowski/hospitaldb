package pl.utp.kradowski.hospitaldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.utp.kradowski.hospitaldb.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("SELECT concat( d.deptName, ' at ',h.hospitalName) FROM Department d,Hospital h " +
            "where d.hospital = h")
    List<String> listAllDepartments();

    @Query(value = "SELECT * from Department d inner join hospital h on d.hospital_id = h.hospital_id " +
            "where d.dept_name like ?1 and h.hospital_name like ?2 "
            ,nativeQuery = true)
    Department getDepartment(String deptname, String hospitalName);

    @Query(value = "SELECT d.dept_id from Department d inner join hospital h on d.hospital_id = h.hospital_id " +
            "where d.dept_name like ?1 and h.hospital_name like ?2 "
            ,nativeQuery = true)
    long getDepartmentId(String deptname, String hospitalName);
}
