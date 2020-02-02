package pl.utp.kradowski.hospitaldb.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;
import pl.utp.kradowski.hospitaldb.view.AccessDeniedView;
import pl.utp.kradowski.hospitaldb.view.LoginView;

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
        if(!SecurityUtils.isAccessGranted(event.getNavigationTarget())){
            if(SecurityUtils.isUserLoggedIn())
                event.rerouteTo(AccessDeniedView.class);
            else event.rerouteTo(LoginView.class);
        }
    }
}
