package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.entity.Duty;
import pl.utp.kradowski.hospitaldb.repository.DutyRepository;
import pl.utp.kradowski.hospitaldb.repository.HospitalEmployeeRepository;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Route(value = WelcomeView.ROUTE, layout = ApplicationViewport.class)
public class WelcomeView extends VerticalLayout {
    public static final String ROUTE = "welcome";

    @Autowired
    public WelcomeView(HospitalEmployeeRepository hospitalEmployeeRepository, DutyRepository dutyRepository){
        LoggedUserProperties userProperties = new LoggedUserProperties(hospitalEmployeeRepository,dutyRepository);
        if(userProperties.currentUsersGroup().equals("ROLE_ADMIN")){
            UI.getCurrent().getPage().setLocation("admin");
        } else {
            H1 currentMonth = new H1();
            FullCalendar calendar = FullCalendarBuilder.create().build();
            calendar.addDatesRenderedListener(event ->{
               currentMonth.setText(event.getIntervalStart().format(DateTimeFormatter.ofPattern("MM-YYY")));
            });
            Button back1M = new Button("<<");
            back1M.addClickListener(click ->{
                calendar.previous();
            });
            Button fwd1M = new Button(">>");
            fwd1M.addClickListener(click -> {
                calendar.next();
            });
            List<Entry> entries = MakeEntries(userProperties.getAllDuties());
            calendar.addEntries(entries);
            add(currentMonth,calendar,new HorizontalLayout(back1M,fwd1M));
        }
    }

    private List<Entry> MakeEntries(List<Duty> allDuties) {
        List<Entry> out = new ArrayList<>();
        for(int i=0;i<allDuties.size();i++){
            Entry e= new Entry();
            String desc = allDuties.get(i).getDept().getDeptName()+
                    " at "+allDuties.get(i).getDept().getHospital().getHospitalName()+
                    " - "+allDuties.get(i).getDutyType().name();
            e.setTitle(desc);
            e.setStart(allDuties.get(i).getStartTime().toInstant());
            e.setEnd(allDuties.get(i).getEndTime().toInstant());
            out.add(e);
        }
        return out;
    }
}
