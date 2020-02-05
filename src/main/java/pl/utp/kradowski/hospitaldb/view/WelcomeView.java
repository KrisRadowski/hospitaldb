package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;

@Route(value = WelcomeView.ROUTE, layout = ApplicationViewport.class)
public class WelcomeView extends VerticalLayout {
    public static final String ROUTE = "welcome";


    @Autowired
    public WelcomeView(){
        LoggedUserProperties userProperties = new LoggedUserProperties();
        if(userProperties.currentUsersGroup().equals("ROLE_ADMIN")){
            UI.getCurrent().getPage().setLocation("admin");
        } else {
            FullCalendar calendar = FullCalendarBuilder.create().build();
            DatePicker datePicker = new DatePicker();
            TimePicker timePicker = new TimePicker();
            add(datePicker,timePicker,calendar);
        }
    }
}
