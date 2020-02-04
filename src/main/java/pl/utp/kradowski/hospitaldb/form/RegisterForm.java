package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.HospitalEmployee;
import pl.utp.kradowski.hospitaldb.entity.Position;
import pl.utp.kradowski.hospitaldb.security.UserRole;
import pl.utp.kradowski.hospitaldb.service.HospitalDBUserService;


import java.util.ArrayList;
import java.util.List;


@Route(RegisterForm.ROUTE)
public class RegisterForm extends VerticalLayout {
    public static final String ROUTE ="register";

    @Autowired
    public RegisterForm(HospitalDBUserService DBUserservice){
        TextField firstName = new TextField("First name: ");
        TextField lastName = new TextField("Last name: ");
        TextField login = new TextField("Login: ");
        PasswordField password = new PasswordField("Password: ");
        add(firstName,lastName,login,password);
        Binder<HospitalEmployee>employeeBinder = new Binder<>();
        employeeBinder.forField(firstName)
                .asRequired("First name needed").bind(HospitalEmployee::getFirstName,HospitalEmployee::setFirstName);
        employeeBinder.forField(lastName)
                .asRequired("Last name needed").bind(HospitalEmployee::getLastName,HospitalEmployee::setLastName);
        Binder<HospitalDBUser> hospitalDBUserBinder = new Binder<>(HospitalDBUser.class);
        hospitalDBUserBinder.forField(login)
                .asRequired("Login needed").bind(HospitalDBUser::getLogin,HospitalDBUser::setLogin);
        hospitalDBUserBinder.forField(password)
                .asRequired("Password needed")
                .withValidator(pass -> pass.length()>=8,"Password must have at least 8 characters")
                .bind(HospitalDBUser::getPassword,HospitalDBUser::setPassword);
        RadioButtonGroup<Position> position = new RadioButtonGroup<>();
        final Position[] chosenPosition = new Position[1];
        position.setItems(SetItemsForRadioButtons(Position.values()));
        position.addValueChangeListener(click -> chosenPosition[0] = click.getValue());
        employeeBinder.forField(position)
                .asRequired("Position needed").bind(HospitalEmployee::getPosition,HospitalEmployee::setPosition);
        add(position);
        Button registerButton = new Button("Register");
        HospitalDBUser user = new HospitalDBUser();
        HospitalEmployee employee = new HospitalEmployee();
        registerButton.addClickListener(click->{
            Exception ex=null;
            try {
                hospitalDBUserBinder.writeBean(user);
                user.setRole(UserRole.ROLE_USER);
                employeeBinder.writeBean(employee);
                DBUserservice.addUser(firstName.getValue(),lastName.getValue(),user,chosenPosition[0]);
            } catch (Exception e){
                ex=e;
            }
            if(ex==null)
                UI.getCurrent().getPage().setLocation("/login");
        });
        add(registerButton);
    }

    private List<Position> SetItemsForRadioButtons(Position[] values) {
        List<Position> out = new ArrayList<>();
        for (Position value : values)
            out.add(value);
        out.remove(0);
        return out;
    }
}
