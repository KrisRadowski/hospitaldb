package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
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
        TextField lastName = new TextField("Last name:: ");
        TextField login = new TextField("Login: ");
        PasswordField password = new PasswordField("Password: ");
        add(firstName,lastName,login,password);
        RadioButtonGroup<String> position = new RadioButtonGroup<>();
        final Position[] chosenPosition = new Position[1];
        position.setItems(SetItemsForRadioButtons(Position.values()));
        position.addValueChangeListener(click -> {
            chosenPosition[0] = Position.valueOf(click.getValue());
        });
        add(position);
        Button registerButton = new Button("Register");
        HospitalDBUser user = new HospitalDBUser();
        registerButton.addClickListener(click->{
            try {
                user.setLogin(login.getValue());
                user.setPassword(password.getValue());
                user.setRole(UserRole.ROLE_USER);
                DBUserservice.addUser(user,chosenPosition[0]);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        add(registerButton);
    }

    private List<String> SetItemsForRadioButtons(Position[] values) {
        List<String> out = new ArrayList<>();
        for (Position value : values)
            out.add(value.name());
        out.remove(0);
        return out;
    }
}
