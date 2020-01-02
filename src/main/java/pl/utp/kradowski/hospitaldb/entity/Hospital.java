package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;

@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "hospital_id")
    private Long id;

    @Column(nullable = false)
    private String hospitalName,street,aptNumber,city,zipCode,phoneNumber,emailAddress;

}
