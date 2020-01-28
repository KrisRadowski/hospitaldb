package pl.utp.kradowski.hospitaldb;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;



@Route(value = "403")
public class AccessDeniedView extends VerticalLayout {
    public AccessDeniedView(){
        Icon deniedIcon = new Icon(VaadinIcon.EXCLAMATION);
        deniedIcon.setSize("400px");
        deniedIcon.setColor("red");
        add(deniedIcon);
        H1 header = new H1("403 Forbidden");
        add(header);
        Text desc = new Text("You're not supposed to be here...");
        add(desc);
        setAlignItems(Alignment.CENTER);
    }
}
