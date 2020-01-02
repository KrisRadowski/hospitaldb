package pl.utp.kradowski.hospitaldb;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route(value = CalendarView.ROUTE)
@PageTitle("Calendar")
public class CalendarView extends VerticalLayout {
    public static final String ROUTE = "calendar";

    private DatePicker datePicker = new DatePicker();
    private TimePicker timePicker = new TimePicker();
    private TextField firstNameField = new TextField();
    private TextField SecondNameField = new TextField();

    public CalendarView(){
        getElement().appendChild(datePicker.getElement()).appendChild(timePicker.getElement());
    }
}
