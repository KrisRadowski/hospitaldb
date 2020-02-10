package pl.utp.kradowski.hospitaldb.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.utp.kradowski.hospitaldb.entity.Department;
import pl.utp.kradowski.hospitaldb.repository.HospitalRepository;
import pl.utp.kradowski.hospitaldb.service.DepartmentService;
import pl.utp.kradowski.hospitaldb.view.ApplicationViewport;


import java.util.List;


@Route(value = "addDepartment",layout = ApplicationViewport.class)
@Secured("ROLE_ADMIN")
public class AddDepartmentForm extends VerticalLayout {

    @Autowired
    public AddDepartmentForm(HospitalRepository hospitalRepository, DepartmentService departmentService){
        ComboBox<String> hospital = new ComboBox<>("Hospital:");
        fillComboBox(hospitalRepository,hospital);
        TextField deptName = new TextField("Department name");
        Button addDepartment = new Button("Add department");
        Button goBack = new Button("Back");
        Binder<Department> departmentBinder = new Binder<>(Department.class);
        departmentBinder.forField(deptName)
                .asRequired("Department name needed").bind(Department::getDeptName,Department::setDeptName);
        Department d = new Department();
        addDepartment.addClickListener(click->{
            Exception ex=null;
            try {
                departmentBinder.writeBean(d);
                d.setHospital(hospitalRepository.findByHospitalName(hospital.getValue()));
                departmentService.addDepartment(d);
            } catch (ValidationException e) {
                ex=e;
            }
            if(ex==null)
                UI.getCurrent().getPage().setLocation("/admin");
        });
        goBack.addClickListener(click-> UI.getCurrent().getPage().setLocation("/admin"));
        add(hospital,deptName,new HorizontalLayout(addDepartment,goBack));
    }

    private void fillComboBox(HospitalRepository hospitalRepository, ComboBox<String> hospital) {
        List<String> hospitalNames = hospitalRepository.listHospitalNames();
        hospital.setItems(hospitalNames);
    }
}
