package pl.utp.kradowski.hospitaldb.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;
import pl.utp.kradowski.hospitaldb.LoginView;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiInitEvent -> {
            final UI ui = uiInitEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    private void beforeEnter(BeforeEnterEvent event) {
        if(!LoginView.class.equals(event.getNavigationTarget())
        &&!SecurityUtils.isUserLoggedIn()){
            //event.rerouteTo(LoginView.class);
        }
    }
}
