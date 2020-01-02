package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "dept_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_id",nullable = false)
    private Hospital hospital;
    @Column(nullable = false)
    private String deptName;

    public Department() {
    }

    public Department(Hospital hospital, String deptName) {
        this.hospital = hospital;
        this.deptName = deptName;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
