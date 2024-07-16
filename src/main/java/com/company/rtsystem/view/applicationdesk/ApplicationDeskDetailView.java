package com.company.rtsystem.view.applicationdesk;

import com.company.rtsystem.entity.ApplicationDesk;

import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "applicationDesks/:id", layout = MainViewTopMenu.class)
@ViewController("ApplicationDesk.detail")
@ViewDescriptor("application-desk-detail-view.xml")
@EditedEntityContainer("applicationDeskDc")
public class ApplicationDeskDetailView extends StandardDetailView<ApplicationDesk> {
}