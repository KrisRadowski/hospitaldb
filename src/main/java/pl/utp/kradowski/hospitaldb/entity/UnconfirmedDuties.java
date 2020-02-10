package pl.utp.kradowski.hospitaldb.entity;

import javax.persistence.*;

@Entity
public class UnconfirmedDuties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "pair_id")
    private Long id;

    @Column(nullable = false)
    private Long duty1,duty2;

    public Long getDuty1() {
        return duty1;
    }

    public void setDuty1(Long duty1) {
        this.duty1 = duty1;
    }

    public Long getDuty2() {
        return duty2;
    }

    public void setDuty2(Long duty2) {
        this.duty2 = duty2;
    }
}
