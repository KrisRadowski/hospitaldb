package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.repository.DepartmentRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalRepository;

@Route(value = "admin",layout = ApplicationViewport.class)
@Secured("ROLE_ADMIN")
public class AdminView extends VerticalLayout {

    @Autowired
    public AdminView(HospitalDBUserRepository u, HospitalRepository h, DepartmentRepository d){
        Label usersCount = new Label("Users count: "+u.count());
        Label hospitalsCount = new Label("Hospitals count: "+h.count());
        Label deptsCount = new Label("Departments count: "+d.count());

    }
}
