package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.entity.Hospital;
import pl.utp.kradowski.hospitaldb.service.HospitalService;

@Route(value = "addHospital")
@Secured("ROLE_ADMIN")
public class AddHospitalForm extends VerticalLayout {

    @Autowired
    public AddHospitalForm(HospitalService hospitalService){
        TextField hospitalName = new TextField("Hospital name:");
        TextField street = new TextField("Street:");
        TextField aptNumber = new TextField("Building number:");
        TextField city = new TextField("City:");
        TextField zipCode = new TextField("ZIP code:");
        TextField phoneNumber = new TextField("Phone number:");
        TextField emailAddress = new TextField("E-mail address:");
        Button addHospital = new Button("Add hospital");
        Hospital h = new Hospital();
        addHospital.addClickListener(click->{
            h.setHospitalName(hospitalName.getValue());
            h.setStreet(street.getValue());
            h.setAptNumber(aptNumber.getValue());
            h.setCity(city.getValue());
            h.setZipCode(zipCode.getValue());
            h.setPhoneNumber(phoneNumber.getValue());
            h.setEmailAddress(emailAddress.getValue());
            hospitalService.addHospital(h);
        });
        add(
                hospitalName,
                street,
                aptNumber,
                city,
                zipCode,
                phoneNumber,
                emailAddress,
                addHospital
        );
    }
}
