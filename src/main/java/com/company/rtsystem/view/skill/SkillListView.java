package com.company.rtsystem.view.skill;

import com.company.rtsystem.entity.Skill;

import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "skills", layout = MainViewTopMenu.class)
@ViewController("Skill.list")
@ViewDescriptor("skill-list-view.xml")
@LookupComponent("skillsDataGrid")
@DialogMode(width = "64em")
public class SkillListView extends StandardListView<Skill> {
}