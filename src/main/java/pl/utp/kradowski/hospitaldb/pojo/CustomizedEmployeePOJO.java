package pl.utp.kradowski.hospitaldb.pojo;

import pl.utp.kradowski.hospitaldb.entity.Position;

public class CustomizedEmployeePOJO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String position;

    public CustomizedEmployeePOJO(Long eId, String firstName, String lastName, Position p){
        this.employeeId = eId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = p.name();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
