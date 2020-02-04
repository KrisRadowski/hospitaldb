package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;

@Route(value = WelcomeView.ROUTE, layout = ApplicationViewport.class)
public class WelcomeView extends VerticalLayout {
    public static final String ROUTE = "welcome";


    @Autowired
    public WelcomeView(){
        FullCalendar calendar = FullCalendarBuilder.create().build();
        DatePicker datePicker = new DatePicker();
        TimePicker timePicker = new TimePicker();
        add(datePicker,timePicker,calendar);
    }
}
