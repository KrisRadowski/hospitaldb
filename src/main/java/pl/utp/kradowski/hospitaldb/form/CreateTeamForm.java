package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;

import java.util.List;

@Route(value = "createTeam",layout = ApplicationViewport.class)
@Secured("ROLE_USER")
public class CreateTeamForm extends VerticalLayout {

    LoggedUserProperties userProperties = new LoggedUserProperties();

    @Autowired
    public CreateTeamForm(HospitalEmployeeRepository employeeRepository){
        ComboBox teamMember1CB = new ComboBox("First team member");
        fillComboBox(employeeRepository,teamMember1CB);
        ComboBox teamMember2CB = new ComboBox("Second team member");
        fillComboBox(employeeRepository,teamMember2CB);
        Button createTeam = new Button("Create team");
        Button goBack = new Button("Back");
        goBack.addClickListener(click-> UI.getCurrent().getPage().setLocation("/welcome"));
        add(teamMember1CB,teamMember2CB,new HorizontalLayout(createTeam,goBack));
    }

    private void fillComboBox(HospitalEmployeeRepository employeeRepository, ComboBox comboBox) {
        List<String> employeeNames = employeeRepository.listEmployees(userProperties.currentUser());
        comboBox.setItems(employeeNames);
    }
}
