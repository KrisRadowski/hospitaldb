package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;
import pl.utp.kradowski.hospitaldb.entity.Team;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;
import pl.utp.kradowski.hospitaldb.service.TeamService;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;

import javax.swing.*;
import java.util.List;

@Route(value = "createTeam",layout = ApplicationViewport.class)
@Secured("ROLE_USER")
public class CreateTeamForm extends VerticalLayout {

    LoggedUserProperties userProperties;

    @Autowired
    public CreateTeamForm(HospitalEmployeeRepository employeeRepository, TeamService teamService){
        userProperties=new LoggedUserProperties(employeeRepository);
        Label info = new Label();
        ComboBox teamMember1CB = new ComboBox("First team member");
        fillComboBox(employeeRepository,teamMember1CB);
        ComboBox teamMember2CB = new ComboBox("Second team member");
        fillComboBox(employeeRepository,teamMember2CB);
        Button createTeam = new Button("Create team");
        createTeam.addClickListener(click -> {
            if(teamMember1CB.getValue().equals(teamMember2CB.getValue())){
                info.setText("You cannot define same team member twice");
            } else {
                Team t = new Team(userProperties.currentUserAsEmployeeEntity(),
                        ExtractEmployeeID(teamMember1CB.getValue()),
                        ExtractEmployeeID(teamMember2CB.getValue()));
                teamService.addTeam(t);
                UI.getCurrent().getPage().setLocation("/welcome");
            }
        });
        Button goBack = new Button("Back");
        goBack.addClickListener(click-> UI.getCurrent().getPage().setLocation("/welcome"));
        add(teamMember1CB,teamMember2CB,new HorizontalLayout(createTeam,goBack),info);
    }

    private Long ExtractEmployeeID(Object value) {
        String cbValue = value.toString();
        String employeeIDInBrackets = cbValue.split(" ")[2];
        return Long.valueOf(StringUtils.substringBetween(employeeIDInBrackets,"(",")"));
    }

    private void fillComboBox(HospitalEmployeeRepository employeeRepository, ComboBox comboBox) {
        List<String> employeeNames = //employeeRepository.listEmployees();
        employeeRepository.listEmployees(userProperties.currentUser(),userProperties.currentUsersPosition());
        comboBox.setItems(employeeNames);
    }
}
