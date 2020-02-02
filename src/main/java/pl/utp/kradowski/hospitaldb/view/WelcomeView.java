package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = WelcomeView.ROUTE)
public class WelcomeView extends VerticalLayout {
    public static final String ROUTE = "welcome";


    @Autowired
    public WelcomeView(){
        getUI().ifPresent(ui -> ui.getPage().setLocation("/admin"));
    }
}
