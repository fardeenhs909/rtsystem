package com.company.rtsystem.view.applicationdesk;

import com.company.rtsystem.entity.ApplicationDesk;

import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "applicationDesks", layout = MainViewTopMenu.class)
@ViewController("ApplicationDesk.list")
@ViewDescriptor("application-desk-list-view.xml")
@LookupComponent("applicationDesksDataGrid")
@DialogMode(width = "64em")
public class ApplicationDeskListView extends StandardListView<ApplicationDesk> {
}