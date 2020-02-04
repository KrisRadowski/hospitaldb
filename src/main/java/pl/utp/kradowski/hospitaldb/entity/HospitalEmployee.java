package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;

@Entity
public class HospitalEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Long id;
    @Column(nullable = false)
    private String firstName, lastName;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private HospitalDBUser hospitalDBUser;
    @Column(nullable = false)
    private Position position;

    public HospitalEmployee(){

    }

    public HospitalEmployee(String fName, String lName, HospitalDBUser u, Position p){
        this.firstName = fName;
        this.lastName = lName;
        this.hospitalDBUser = u;
        this.position = p;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToOne(cascade = CascadeType.MERGE)
    public HospitalDBUser getHospitalDBUser() {
        return hospitalDBUser;
    }

    public void setHospitalDBUser(HospitalDBUser hospitalDBUser) {
        this.hospitalDBUser = hospitalDBUser;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
