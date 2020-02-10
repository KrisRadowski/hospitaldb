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



import java.time.format.DateTimeFormatter;

@Route(value = WelcomeView.ROUTE, layout = ApplicationViewport.class)
public class WelcomeView extends VerticalLayout {
    public static final String ROUTE = "welcome";


    @Autowired
    public WelcomeView(){
        LoggedUserProperties userProperties = new LoggedUserProperties();
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
            Entry[] entries = MakeEntries()
            calendar.addEntries();
            add(currentMonth,calendar,new HorizontalLayout(back1M,fwd1M));
        }
    }
}
