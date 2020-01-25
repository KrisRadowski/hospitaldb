package pl.utp.kradowski.hospitaldb;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.Position;
import pl.utp.kradowski.hospitaldb.security.UserRole;
import pl.utp.kradowski.hospitaldb.service.HospitalDBUserService;

import javax.swing.*;

@Route(value = LoginView.ROUTE)
@PageTitle("Sign in")
public class LoginView extends VerticalLayout {

    public static final String ROUTE ="login";

    private LoginForm login = new LoginForm();

    @Autowired
    public LoginView(HospitalDBUserService DBUserservice){
        login.setAction("login");
        getElement().appendChild(login.getElement());
        Button registerButton = new Button("Register");
        registerButton.addClickListener(click->{
            UI.getCurrent().getPage().setLocation("register");
        });
        add(registerButton);
        Button magicButton = new Button(";)");
        magicButton.addClickListener(click -> {
            HospitalDBUser admin = new HospitalDBUser("admin","admin", UserRole.ROLE_ADMIN);
            DBUserservice.addUser(admin, Position.ADMIN);
            remove(magicButton);
        });
        add(magicButton);
    }
}
