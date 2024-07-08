package com.company.rtsystem.view.user;

import com.company.rtsystem.entity.User;
import com.company.rtsystem.view.main.MainView;
import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "users", layout = MainViewTopMenu.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {
}