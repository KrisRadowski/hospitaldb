package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.repository.DepartmentRepository;
import pl.utp.kradowski.hospitaldb.repository.DutyRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;
import pl.utp.kradowski.hospitaldb.repository.TeamRepository;
import pl.utp.kradowski.hospitaldb.service.DutyService;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(value = "replaceTeam",layout = ApplicationViewport.class)
@Secured("ROLE_USER")
public class ReplaceTeamForm extends VerticalLayout {

    LoggedUserProperties userProperties;
    ComboBox<String> dutiesCB,dutiesToReplaceCB;

    @Autowired
    public ReplaceTeamForm(HospitalEmployeeRepository hospitalEmployeeRepository, TeamRepository teamRepository,
                           DepartmentRepository departmentRepository,DutyRepository dutyRepository, DutyService dutyService){

        userProperties= new LoggedUserProperties(hospitalEmployeeRepository,teamRepository);
        List<Duty> dutiesList = dutyRepository.getAllDuties(userProperties.getUsersTeam().getId());
        List<Duty> dutiesToReplaceList = dutyRepository.getAllDutiesToReplace(userProperties.getUsersTeam().getId());
        dutiesCB = new ComboBox<>("Duty to give");
        dutiesToReplaceCB = new ComboBox<>("Duty to Replace");
        fillCBs(dutiesList,dutiesToReplaceList);
        Button save = new Button("Save");
        Label info = new Label();
        save.addClickListener(click->{
            if(dutiesCB.getValue()!=null&&dutiesToReplaceCB.getValue()!=null){
                Date startDate = new Date(),endDate=new Date();
                String[] extractedDutyToBeGiven=ExtractInfo(dutiesCB.getValue());
                String[] extractedDept = ExtractDept(extractedDutyToBeGiven[0]);
                try {
                    startDate = new SimpleDateFormat("dd.MM.yy HH:mm").parse(extractedDutyToBeGiven[1]);
                    endDate = new SimpleDateFormat("dd.MM.yy HH:mm").parse(extractedDutyToBeGiven[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Duty dutyToBeGiven = dutyRepository.getDuty(startDate,endDate,userProperties.getUsersTeam().getId(),departmentRepository.getDepartmentId(extractedDept[0],extractedDept[1]));
                String[] extractedDutyToReplace = ExtractInfo(dutiesToReplaceCB.getValue());
                extractedDept = ExtractDept(extractedDutyToReplace[0]);
                try {
                    startDate = new SimpleDateFormat("dd.MM.yy HH:mm").parse(extractedDutyToReplace[1]);
                    endDate = new SimpleDateFormat("dd.MM.yy HH:mm").parse(extractedDutyToReplace[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Duty dutyToReplace = dutyRepository.getDuty(startDate,endDate,ExtractTeamId(extractedDutyToReplace[3]),
                        departmentRepository.getDepartmentId(extractedDept[0],extractedDept[1]));
                Duty newDuty = new Duty(),newDutyForOtherSide= new Duty();
                newDuty.setConfirmed(false);
                newDuty.setTeam(userProperties.getUsersTeam());
                newDuty.setDept(dutyToReplace.getDept());
                newDuty.setDutyType(dutyToReplace.getDutyType());
                newDuty.setStartTime(dutyToReplace.getStartTime());
                newDuty.setEndTime(dutyToReplace.getEndTime());
                newDutyForOtherSide.setConfirmed(false);
                newDutyForOtherSide.setTeam(dutyToReplace.getTeam());
                newDutyForOtherSide.setDept(dutyToBeGiven.getDept());
                newDutyForOtherSide.setDutyType(dutyToBeGiven.getDutyType());
                newDutyForOtherSide.setStartTime(dutyToBeGiven.getStartTime());
                newDutyForOtherSide.setEndTime(dutyToBeGiven.getEndTime());
                dutyService.addDuty(newDuty);
                dutyService.addDuty(newDutyForOtherSide);
                UI.getCurrent().getPage().setLocation("welcome");
            }
            else {
                info.setText("Duties not selected");
            }
        });
        add(dutiesCB,dutiesToReplaceCB,save,info);
    }

    private long ExtractTeamId(String s){
        return Long.parseLong(StringUtils.substringBetween(s,"(",")"));
    }

    private String[] ExtractDept(String s) {
        return s.split(" at ");
    }

    private String[] ExtractInfo(String value) {
        return value.split(" - ");
    }

    private void fillCBs(List<Duty> dutiesList, List<Duty> dutiesToReplaceList) {
        List<String> filling = new ArrayList<>(),filling2=new ArrayList<>();
        for(Duty duty:dutiesList){
            String start = new SimpleDateFormat().format(duty.getStartTime());
            String end = new SimpleDateFormat().format(duty.getEndTime());
            String dept = duty.getDept().getDeptName();
            String hospital = duty.getDept().getHospital().getHospitalName();
            filling.add(dept + " at " + hospital + " - " + start + " - " + end);
        }
        dutiesCB.setItems(filling);
        for (Duty duty : dutiesToReplaceList) {
            String start = new SimpleDateFormat().format(duty.getStartTime());
            String end = new SimpleDateFormat().format(duty.getEndTime());
            String teamLeader = duty.getTeam().getTeamLeader().getFirstName() + " " +
                    duty.getTeam().getTeamLeader().getLastName()+" ("+duty.getTeam().getId()+")";
            String dept = duty.getDept().getDeptName();
            String hospital = duty.getDept().getHospital().getHospitalName();
            filling2.add(dept + " at " + hospital + " - " + start + " - " + end + " - " + teamLeader);
        }
        dutiesToReplaceCB.setItems(filling2);
    }
}
