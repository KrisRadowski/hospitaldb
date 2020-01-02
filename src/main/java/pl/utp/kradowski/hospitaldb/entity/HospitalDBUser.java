package pl.utp.kradowski.hospitaldb.entity;

import pl.utp.kradowski.hospitaldb.security.UserRole;

import javax.persistence.*;

@Entity
public class HospitalDBUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isAccountNotExpired;
    private boolean isNotLocked;
    private boolean isPasswordNotExpired;
    private boolean isEnabled;

    protected HospitalDBUser(){}

    public HospitalDBUser(String login, String password){
        this.login = login;
        this.password = password;
    }

    public Long getId(){
        return id;
    }

    public String getLogin(){
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isAccountNotExpired() {
        return isAccountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        isAccountNotExpired = accountNotExpired;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    public boolean isPasswordNotExpired() {
        return isPasswordNotExpired;
    }

    public void setPasswordNotExpired(boolean passwordNotExpired) {
        isPasswordNotExpired = passwordNotExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
