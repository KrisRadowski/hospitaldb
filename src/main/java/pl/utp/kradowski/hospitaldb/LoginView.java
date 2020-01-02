package pl.utp.kradowski.hospitaldb;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = LoginView.ROUTE)
@PageTitle("Sign in")
public class LoginView extends VerticalLayout {

    public static final String ROUTE ="login";

    private LoginForm login = new LoginForm();

    public LoginView(){
        login.setAction("login");
        getElement().appendChild(login.getElement());
    }
}
