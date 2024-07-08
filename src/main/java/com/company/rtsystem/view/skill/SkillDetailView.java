package com.company.rtsystem.view.skill;

import com.company.rtsystem.entity.Skill;

import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "skills/:id", layout = MainViewTopMenu.class)
@ViewController("Skill.detail")
@ViewDescriptor("skill-detail-view.xml")
@EditedEntityContainer("skillDc")
public class SkillDetailView extends StandardDetailView<Skill> {
}