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
}
