package pl.utp.kradowski.hospitaldb;


import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.Position;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;
import pl.utp.kradowski.hospitaldb.security.UserRole;
import pl.utp.kradowski.hospitaldb.service.HospitalDBUserService;

import java.util.Collections;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Sign in")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    public static final String ROUTE ="login";
    private LoginForm login = new LoginForm();

    @Autowired
    public LoginView(HospitalDBUserRepository hospitalDBUserRepository, HospitalDBUserService hospitalDBUserService){
        login.setAction("login");
        login.setEnabled(true);
        getElement().appendChild(login.getElement());
        Button registerButton = new Button("Register");
        registerButton.addClickListener(click->{
            UI.getCurrent().getPage().setLocation("register");
        });
        add(registerButton);
        if(hospitalDBUserRepository.findByLogin("admin")==null){
            Button magicButton = new Button(";)");
            magicButton.addClickListener(click ->{
                HospitalDBUser user = new HospitalDBUser("admin","admin", UserRole.ROLE_ADMIN);
                hospitalDBUserService.addUser(user, Position.ADMIN);
                remove(magicButton);
            });
            add(magicButton);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        /*System.out.println(beforeEnterEvent.getLocation().getQueryParameters().getParameters());
        if(!beforeEnterEvent.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
            login.setError(true);
        }*/
        login.setError(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

    private LoginI18n pl(){
        final LoginI18n i18n = LoginI18n.createDefault();
        i18n.getErrorMessage().setMessage(":(");
        return i18n;
    }
}
