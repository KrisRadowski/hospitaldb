package pl.utp.kradowski.hospitaldb.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;

;

public class CRUDView extends VerticalLayout {

    //private HospitalDBUserRepositoryImpl repository = new HospitalDBUserRepositoryImpl(null);
    private Grid<HospitalDBUser> grid = new Grid<>(HospitalDBUser.class);
    private TextField filterText = new TextField();


    public CRUDView(){
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e->updateList());
        grid.setColumns("Login", "Password", "Acc. type");
        add(filterText,grid);
        setSizeFull();
        updateList();
    }

    private void updateList() {
        //grid.setItems((Collection<HospitalDBUser>) repository.findByHospitalDBUsername(filterText.getValue()));
    }
}
