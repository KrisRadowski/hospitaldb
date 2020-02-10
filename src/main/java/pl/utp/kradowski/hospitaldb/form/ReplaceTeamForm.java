package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.repository.DutyRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;
import pl.utp.kradowski.hospitaldb.repository.TeamRepository;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Route(value = "replaceTeam",layout = ApplicationViewport.class)
@Secured("ROLE_USER")
public class ReplaceTeamForm extends VerticalLayout {

    LoggedUserProperties userProperties;
    ComboBox<String> dutiesCB;

    @Autowired
    public ReplaceTeamForm(HospitalEmployeeRepository hospitalEmployeeRepository, TeamRepository teamRepository, DutyRepository dutyRepository){

        userProperties= new LoggedUserProperties(hospitalEmployeeRepository,teamRepository);
        List<Duty> dutiesList = dutyRepository.getAllDuties(userProperties.getUsersTeam().getId());
        dutiesCB = new ComboBox<>();
        fillDutiesCB(dutiesList);
        Button save = new Button("Save");
        add(dutiesCB,save);
    }

    private void fillDutiesCB(List<Duty> dutiesList) {
        List<String> filling = new ArrayList<>();
        for(int i=0;i<dutiesList.size();i++){
            dutiesList.get(i).getStartTime();
            filling.add(Integer.toString(i));
        }
    }
}
