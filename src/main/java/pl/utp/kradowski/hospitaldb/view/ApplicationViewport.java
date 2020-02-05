package pl.utp.kradowski.hospitaldb.view;

import com.github.appreciated.app.layout.component.applayout.TopLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.top.builder.TopAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.top.item.TopClickableItem;
import com.github.appreciated.app.layout.component.menu.top.item.TopNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.springframework.stereotype.Component;
import pl.utp.kradowski.hospitaldb.controller.LoggedUserProperties;
import pl.utp.kradowski.hospitaldb.form.AddDepartmentForm;
import pl.utp.kradowski.hospitaldb.form.AddHospitalForm;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(Material.class)
@Component @UIScope
public class ApplicationViewport extends AppLayoutRouterLayout<TopLayouts.Top> {
    public ApplicationViewport(){
        LoggedUserProperties userProperties = new LoggedUserProperties();
        if(userProperties.currentUsersGroup().equals("ROLE_ADMIN")){
            init(AppLayoutBuilder.get(TopLayouts.Top.class)
                    .withAppMenu(TopAppMenuBuilder.get()
                            .add(new TopClickableItem("Add Hospital",VaadinIcon.PLUS.create(),
                                            click->UI.getCurrent().getPage().setLocation("addHospital")),
                                    new TopClickableItem("Add Department",VaadinIcon.PLUS.create(),
                                            click->UI.getCurrent().getPage().setLocation("addDepartment")),
                                    new TopClickableItem("Sign out", VaadinIcon.EXIT.create(),
                                    click -> UI.getCurrent().getPage().setLocation("logout")))
                            .build())
                    .build());
        }
        if(userProperties.currentUsersGroup().equals("ROLE_USER")){
            init(AppLayoutBuilder.get(TopLayouts.Top.class)
                    .withAppMenu(TopAppMenuBuilder.get()
                            .add(new TopClickableItem("Create team",VaadinIcon.PLUS.create(),
                                            click -> UI.getCurrent().getPage().setLocation("createTeam"))
                                    ,new TopClickableItem("Sign out", VaadinIcon.EXIT.create(),
                                    click -> UI.getCurrent().getPage().setLocation("logout")))
                            .build())
                    .build());
        }
    }
}
