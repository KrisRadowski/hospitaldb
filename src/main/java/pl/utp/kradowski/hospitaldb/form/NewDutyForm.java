package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.entity.DutyType;
import pl.utp.kradowski.hospitaldb.repository.DepartmentRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;
import pl.utp.kradowski.hospitaldb.repository.TeamRepository;
import pl.utp.kradowski.hospitaldb.service.DutyService;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Route(value = "newDuty",layout = ApplicationViewport.class)
@Secured("ROLE_USER")
public class NewDutyForm extends VerticalLayout {

    ComboBox<String> dept,dutyType;
    LoggedUserProperties userProperties;

    @Autowired
    public NewDutyForm(DepartmentRepository departmentRepository, HospitalEmployeeRepository hospitalEmployeeRepository,
                       TeamRepository teamRepository, DutyService service){
        userProperties = new LoggedUserProperties(hospitalEmployeeRepository,teamRepository);
        DatePicker datePicker = new DatePicker("Date");
        TimePicker start=new TimePicker("Start"),stop = new TimePicker("End");
        dept = new ComboBox<>("Department");
        dutyType=new ComboBox<>("Duty type");
        fillDeptComboBox(departmentRepository);
        fillDutyTypeComboBox();
        Button saveDuty = new Button("Save");
        Label info = new Label();
        saveDuty.addClickListener(click ->{
            if(datePicker.getValue()==null||
            start.getValue()==null||
            stop.getValue()==null||
            dept.getValue()==null||
            dutyType.getValue()==null)
                info.setText("Not all values are set");
            else if (datePicker.getValue().isBefore(LocalDate.now())||(
                    datePicker.getValue().isEqual(LocalDate.now())&&(
                            start.getValue().isBefore(LocalTime.now())||
                            stop.getValue().isBefore(LocalTime.now()))))
                info.setText("You can't set time at the past");
            else if (!stop.getValue().isAfter(start.getValue()))
                info.setText("You set time wrong");
            else {
                Duty d = new Duty();
                d.setConfirmed(true);
                d.setStartTime(Date.from(datePicker.getValue().atTime(start.getValue()).atZone(ZoneId.systemDefault()).toInstant()));
                d.setEndTime(Date.from(datePicker.getValue().atTime(stop.getValue()).atZone(ZoneId.systemDefault()).toInstant()));
                d.setTeam(userProperties.getUsersTeam());
                d.setDept(departmentRepository.getDepartment(ExtractInfo(dept.getValue())[0],ExtractInfo(dept.getValue())[1]));
                switch (dutyType.getValue()){
                    case "Low priority":
                        d.setDutyType(DutyType.LOW_PRIORITY);
                        break;
                    case "Normal":
                        d.setDutyType(DutyType.NORMAL);
                        break;
                    case "Emergency":
                        d.setDutyType(DutyType.EMERGENCY);
                        break;
                }
                service.addDuty(d);
                UI.getCurrent().getPage().setLocation("welcome");
            }
        });
        add(datePicker,start,stop,dept,dutyType,saveDuty,info);
    }

    private String[] ExtractInfo(String value) {
        return value.split(" at ");
    }

    private void fillDutyTypeComboBox() {
        List<String> filling = new ArrayList<>();
        filling.add("Low priority");
        filling.add("Normal");
        filling.add("Emergency");
        dutyType.setItems(filling);
    }

    private void fillDeptComboBox(DepartmentRepository repository) {
        dept.setItems(repository.listAllDepartments());
    }


}
