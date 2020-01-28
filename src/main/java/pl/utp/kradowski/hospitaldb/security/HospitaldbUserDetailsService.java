package pl.utp.kradowski.hospitaldb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class HospitaldbUserDetailsService implements UserDetailsService {
    private HospitalDBUserRepository hospitalDBUserRepository;

    @Autowired
    public void setHospitalDBUserRepository(HospitalDBUserRepository hospitalDBUserRepository) {
        this.hospitalDBUserRepository = hospitalDBUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HospitalDBUser user = hospitalDBUserRepository.findByLogin(s);
        if (user==null)
            throw new UsernameNotFoundException("No user of this login");
        return new User(user.getLogin(),user.getPassword(),grantAuthority(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> grantAuthority(UserRole role) {
        Collection<GrantedAuthority> out = new HashSet<>();
        out.add(new SimpleGrantedAuthority(role.name()));
        return out;
    }
}
