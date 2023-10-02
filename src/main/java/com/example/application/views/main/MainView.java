package com.example.application.views.main;

import com.example.application.views.GridProMain;
import com.example.application.views.GridProMainAndamento;
import com.example.application.views.GridProMainConcluida;
import com.example.application.views.GridProMainPendente;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.sql.SQLException;

@Route("")
public class MainView extends AppLayout {

    public MainView() {
        H1 appTitle = new H1("Minhas tarefas");
        appTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("line-height", "var(--lumo-size-l)")
                .set("margin", "0 var(--lumo-space-m)");

        Tabs views = getPrimaryNavigation();

        DrawerToggle toggle = new DrawerToggle();

        H2 viewTitle = new H2("Tarefas");
        viewTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs subViews = getSecondaryNavigation();

        HorizontalLayout wrapper = new HorizontalLayout(toggle, viewTitle);
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        wrapper.setSpacing(false);

        VerticalLayout viewHeader = new VerticalLayout(wrapper, subViews);
        viewHeader.setPadding(false);
        viewHeader.setSpacing(false);

        addToDrawer(appTitle, views);
        addToNavbar(viewHeader);

        setPrimarySection(Section.DRAWER);

        views.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab != null) {
                String tabName = selectedTab.getLabel();
                if (tabName.equals("Dashboard")) {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                    }
                } else if (tabName.equals("Tarefas")) {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                        setContent(new GridProMain());

                    } else  {
                        setContent(new GridProMain());
                    }
                }
            }
        });

        subViews.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab != null) {
                String tabName = selectedTab.getLabel();
                if (tabName.equals("Todas")) {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                        setContent(new GridProMain());
                    } else {
                        setContent(new GridProMain());
                    }
                } else if (tabName.equals("Pendentes")) {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                        setContent(new GridProMainPendente());
                    } else {
                        setContent(new GridProMainPendente());
                    }
                } else if (tabName.equals("Andamento")) {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                        setContent(new GridProMainAndamento());
                    } else {
                        setContent(new GridProMainAndamento());
                    }
                } else if (tabName.equals("Concluida")) {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                        setContent(new GridProMainConcluida());
                    } else {
                        setContent(new GridProMainConcluida());
                    }
                } else {
                    if (getContent()!=null) {
                        getContent().removeFromParent();
                    }
                }
            }
        });
    }

    private Tabs getPrimaryNavigation() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.DASHBOARD, "Dashboard"),
                createTab(VaadinIcon.LIST, "Tarefas"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setSelectedIndex(0);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));

        link.setTabIndex(-1);

        Tab tab = new Tab(link);
        tab.setLabel(viewName);

        return tab;
    }

    private Tabs getSecondaryNavigation() {
        Tabs tabs = new Tabs();
        tabs.add(new Tab("Todas"), new Tab("Pendentes"), new Tab("Andamento"),
                new Tab("Concluida"));
        return tabs;
    }

}
