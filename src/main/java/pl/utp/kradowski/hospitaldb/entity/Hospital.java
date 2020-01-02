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

    public Hospital() {
    }

    public Hospital(String hospitalName, String street, String aptNumber, String city, String zipCode, String phoneNumber, String emailAddress) {
        this.hospitalName = hospitalName;
        this.street = street;
        this.aptNumber = aptNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
