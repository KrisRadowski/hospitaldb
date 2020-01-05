package pl.utp.kradowski.hospitaldb;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.entity.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Route(RegisterForm.ROUTE)
public class RegisterForm extends VerticalLayout {
    public static final String ROUTE ="register";

    @Autowired
    public RegisterForm(){
        TextField firstName = new TextField("First name: ");
        TextField lastName = new TextField("Last name:: ");
        TextField login = new TextField("Login: ");
        PasswordField password = new PasswordField("Password: ");
        add(firstName,lastName,login,password);
        RadioButtonGroup<String> position = new RadioButtonGroup<>();
        AtomicReference<Position> chosenPosition = new AtomicReference<>();
        position.setItems(SetItemsForRadioButtons(Position.values()));
        position.addValueChangeListener(click -> {
            chosenPosition.set(Position.valueOf(click.getValue()));
        });
        add(position);
        Button registerButton = new Button("Register");
        registerButton.addClickListener(click->{
            try {
                HospitalDBUser user = new HospitalDBUser(login.getValue(),password.getValue());

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
