package pl.utp.kradowski.hospitaldb.view;


import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepository;
import pl.utp.kradowski.hospitaldb.security.UserRole;
import pl.utp.kradowski.hospitaldb.service.HospitalDBUserService;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Sign in")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    public static final String ROUTE ="login";
    private LoginForm login = new LoginForm();

    public LoginView(HospitalDBUserRepository hospitalDBUserRepository, HospitalDBUserService hospitalDBUserService){
        login.setAction("login");
        login.setEnabled(true);
        getElement().appendChild(login.getElement());
        Button registerButton = new Button("Register");
        registerButton.addClickListener(click-> UI.getCurrent().getPage().setLocation("register"));
        add(registerButton);
        if(hospitalDBUserRepository.findByLogin("admin")==null){
            Button magicButton = new Button(";)");
            magicButton.addClickListener(click ->{
                HospitalDBUser user = new HospitalDBUser("admin","admin", UserRole.ROLE_ADMIN);
                hospitalDBUserService.addUser(user);
                remove(magicButton);
            });
            add(magicButton);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        login.setError(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
