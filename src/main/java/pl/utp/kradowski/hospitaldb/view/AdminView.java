package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "admin",layout = ApplicationViewport.class)
@Secured("ROLE_ADMIN")
public class AdminView extends HorizontalLayout {
    Button addHospital = new Button("Add Hospital");
    Button addDepartment = new Button("Add Department");

    @Autowired
    public AdminView(){

    }
}
