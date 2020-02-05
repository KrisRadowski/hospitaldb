package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.entity.Hospital;
import pl.utp.kradowski.hospitaldb.service.HospitalService;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;

@Route(value = "addHospital",layout = ApplicationViewport.class)
@Secured("ROLE_ADMIN")
public class AddHospitalForm extends VerticalLayout {

    @Autowired
    public AddHospitalForm(HospitalService hospitalService){
        Binder<Hospital> hospitalBinder = new Binder<>(Hospital.class);
        TextField hospitalName = new TextField("Hospital name:");
        TextField street = new TextField("Street:");
        TextField aptNumber = new TextField("Building number:");
        TextField city = new TextField("City:");
        TextField zipCode = new TextField("ZIP code:");
        TextField phoneNumber = new TextField("Phone number:");
        EmailField emailAddress = new EmailField("E-mail address:");
        Button addHospital = new Button("Add hospital");
        Button goBack = new Button("Back");
        Hospital h = new Hospital();
        hospitalBinder.forField(hospitalName)
                .asRequired("Hospital name needed").bind(Hospital::getHospitalName,Hospital::setHospitalName);
        hospitalBinder.forField(street)
                .asRequired("Street needed").bind(Hospital::getStreet,Hospital::setStreet);
        hospitalBinder.forField(aptNumber)
                .asRequired("Building number needed")
                .withValidator(number -> number.matches("[0-9]+"),"It's not a number")
                .bind(Hospital::getAptNumber,Hospital::setAptNumber);
        hospitalBinder.forField(city)
                .asRequired("City needed").bind(Hospital::getCity,Hospital::setCity);
        hospitalBinder.forField(zipCode)
                .asRequired("ZIP code needed")
                .withValidator(code -> code.matches("^-?\\d{2}-\\d{3}$"),"Not a valid ZIP code")
                .bind(Hospital::getZipCode,Hospital::setZipCode);
        hospitalBinder.forField(phoneNumber)
                .asRequired("Phone number needed")
                .withValidator(number -> (number.length() == 9&&number.matches("[0-9]+")),"Phone number has 9 digits")
                .bind(Hospital::getPhoneNumber,Hospital::setPhoneNumber);
        hospitalBinder.forField(emailAddress)
                .asRequired()
                .withValidator(new EmailValidator("It's not a valid e-mail address"))
                .bind(Hospital::getEmailAddress,Hospital::setEmailAddress);
        addHospital.addClickListener(click->{
            Exception ex=null;
            try {
                hospitalBinder.writeBean(h);
                hospitalService.addHospital(h);
            } catch (ValidationException e) {
                ex=e;
            }
            if(ex==null)
                UI.getCurrent().getPage().setLocation("/admin");
        });
        goBack.addClickListener(click-> UI.getCurrent().getPage().setLocation("/admin"));
        add(
                hospitalName,
                street,
                aptNumber,
                city,
                zipCode,
                phoneNumber,
                emailAddress,
                new HorizontalLayout(addHospital,goBack)
        );
    }
}
