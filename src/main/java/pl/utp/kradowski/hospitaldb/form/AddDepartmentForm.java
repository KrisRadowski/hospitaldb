package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.entity.Department;
import pl.utp.kradowski.hospitaldb.entity.Hospital;
import pl.utp.kradowski.hospitaldb.service.DepartmentService;


@Route(value = "addDepartment")
@Secured("ROLE_ADMIN")
public class AddDepartmentForm extends VerticalLayout {

    @Autowired
    public AddDepartmentForm(DepartmentService departmentService){
        ComboBox<Hospital> hospital = new ComboBox<>();
        TextField deptName = new TextField("Department name");
        Button addDepartment = new Button("Add department");
        addDepartment.addClickListener(click->{
            Department d = new Department();
            d.setHospital(hospital.getValue());
            d.setDeptName(deptName.getValue());
            departmentService.addDepartment(d);
        });
    }
}
