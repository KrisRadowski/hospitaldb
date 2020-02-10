package pl.utp.kradowski.hospitaldb.view;

import com.github.appreciated.app.layout.component.applayout.TopLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.top.builder.TopAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.top.item.TopClickableItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.entity.UnconfirmedDuties;
import pl.utp.kradowski.hospitaldb.repository.DutyRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;
import pl.utp.kradowski.hospitaldb.repository.TeamRepository;
import pl.utp.kradowski.hospitaldb.repository.UnconfirmedDutiesRepository;
import pl.utp.kradowski.hospitaldb.service.TeamService;
import pl.utp.kradowski.hospitaldb.service.UnconfirmedDutiesService;

import java.text.SimpleDateFormat;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(Material.class)
@Component @UIScope
public class ApplicationViewport extends AppLayoutRouterLayout<TopLayouts.Top> {
    DutyRepository d;
    @Autowired
    public ApplicationViewport(HospitalEmployeeRepository employeeRepository, TeamRepository teamRepository,
                               DutyRepository dutyRepository, UnconfirmedDutiesRepository u,TeamService teamService){
        LoggedUserProperties userProperties = new LoggedUserProperties(employeeRepository,teamRepository,dutyRepository);
        this.d=dutyRepository;
        if(userProperties.currentUsersGroup().equals("ROLE_ADMIN")){
            init(AppLayoutBuilder.get(TopLayouts.Top.class)
                    .withAppMenu(TopAppMenuBuilder.get()
                            .add(new TopClickableItem("Add Hospital",VaadinIcon.PLUS.create(),
                                            click->UI.getCurrent().getPage().setLocation("addHospital")),
                                    new TopClickableItem("Add Department",VaadinIcon.PLUS.create(),
                                            click->UI.getCurrent().getPage().setLocation("addDepartment")),
                                    new TopClickableItem("", VaadinIcon.EXIT.create(),
                                    click -> UI.getCurrent().getPage().setLocation("logout")))
                            .build())
                    .build());
        }
        if(userProperties.currentUsersGroup().equals("ROLE_USER")){
            if(!userProperties.userIsTeamLeader()&&!userProperties.userBelongsInTeam())
            init(AppLayoutBuilder.get(TopLayouts.Top.class)
                    .withAppMenu(TopAppMenuBuilder.get()
                            .add(new TopClickableItem("Create team",VaadinIcon.PLUS.create(),
                                    click -> UI.getCurrent().getPage().setLocation("createTeam")))
                            .addToSection(FOOTER, new TopClickableItem("", VaadinIcon.EXIT.create(),
                                    click -> UI.getCurrent().getPage().setLocation("logout")))
                            .build())
                    .build());
            else if(userProperties.userIsTeamLeader()){
                Button confirmDeletion = new Button("Yes");
                Button cancelDeletion = new Button("no");
                confirmDeletion.addClickListener(click -> {
                    teamService.deleteTeam(userProperties.getUsersTeam());
                    UI.getCurrent().getPage().setLocation("welcome");
                });
                Notification n = new Notification(new Label("Are you sure to delete your team?"),confirmDeletion,cancelDeletion);
                cancelDeletion.addClickListener(click ->n.close());
                n.setPosition(Notification.Position.MIDDLE);

                Label confirmationLabel = new Label();
                Button confirmReplace = new Button("Confirm");
                Button cancelReplace = new Button("Refuse");
                Notification dutyConfirmation = new Notification(confirmationLabel,confirmReplace,cancelReplace);
                dutyConfirmation.setPosition(Notification.Position.MIDDLE);
                Icon bell = new Icon(VaadinIcon.BELL);
                ComponentEventListener<ClickEvent<?>> bellClickEvent = (ComponentEventListener<ClickEvent<?>>) clickEvent -> {};
                if(userProperties.userHasUnconfirmedDuties()){
                    bell.setColor("red");
                    bellClickEvent = clickEvent -> dutyConfirmation.open();
                    UnconfirmedDuties unconfirmedDuties = u.getByDuty2(userProperties.unconfirmedDuty());
                    Duty duty1 = dutyRepository.getDutyById(unconfirmedDuties.getDuty1());
                    Duty duty2 = dutyRepository.getDutyById(unconfirmedDuties.getDuty2());
                    String name = duty1.getTeam().getTeamLeader().getFirstName()+" "+
                            duty1.getTeam().getTeamLeader().getLastName();
                    String duty1Details = DutyDetails(duty1);
                    String duty2Details = DutyDetails(duty2);
                    confirmationLabel.setText(name + " want to replace duty "+duty1Details+" for duty "+duty2Details);
                    confirmReplace.addClickListener(click ->{
                        dutyConfirmation.close();
                        replaceDuties(duty1,duty2);
                        u.delete(unconfirmedDuties);
                        UI.getCurrent().getPage().reload();
                    });
                    cancelReplace.addClickListener(click -> {
                        dutyConfirmation.close();
                        deleteDutiesToReplace(duty1,duty2);
                        u.delete(unconfirmedDuties);
                        UI.getCurrent().getPage().reload();
                    });
                }
                TopClickableItem bellItem = new TopClickableItem("",bell,bellClickEvent);
                init(AppLayoutBuilder.get(TopLayouts.Top.class)
                        .withAppMenu(TopAppMenuBuilder.get()
                                .add(new TopClickableItem("Report for Duty",VaadinIcon.PLUS.create(),
                                        click -> UI.getCurrent().getPage().setLocation("newDuty")))
                                .add(new TopClickableItem("Replace Team",VaadinIcon.REFRESH.create(),
                                        clickEvent -> UI.getCurrent().getPage().setLocation("replaceTeam")))
                                .add(new TopClickableItem("Delete team", VaadinIcon.DEL.create(),
                                        click -> n.open()))
                                .addToSection(FOOTER,bellItem)
                                .addToSection(FOOTER, new TopClickableItem("", VaadinIcon.EXIT.create(),
                                        click -> UI.getCurrent().getPage().setLocation("logout")))
                                .build())
                        .build());
            } else {
                init(AppLayoutBuilder.get(TopLayouts.Top.class)
                        .withAppMenu(TopAppMenuBuilder.get()
                                .addToSection(FOOTER, new TopClickableItem("", VaadinIcon.EXIT.create(),
                                        click -> UI.getCurrent().getPage().setLocation("logout")))
                                .build())
                        .build());
            }
        }
    }

    private void replaceDuties(Duty duty1, Duty duty2) {
        d.setDutyAsConfirmed(duty1.getId());
        d.setDutyAsConfirmed(duty2.getId());
        Duty[] dutiesToDeletion = new Duty[2];
        dutiesToDeletion[0]=d.getDuty(duty1.getStartTime(),duty1.getEndTime(),duty2.getTeam().getId(),duty1.getDept().getId());
        dutiesToDeletion[1]=d.getDuty(duty2.getStartTime(),duty2.getEndTime(),duty1.getTeam().getId(),duty2.getDept().getId());
        d.delete(dutiesToDeletion[0]);
        d.delete(dutiesToDeletion[1]);
    }

    private void deleteDutiesToReplace(Duty duty1, Duty duty2) {
        d.delete(duty1);
        d.delete(duty2);
    }

    private String DutyDetails(Duty duty) {
        return "from "+new SimpleDateFormat().format(duty.getStartTime())+" to "+
                new SimpleDateFormat().format(duty.getEndTime())+" at "+
                duty.getDept().getDeptName()+" in "+duty.getDept().getHospital().getHospitalName();
    }
}
