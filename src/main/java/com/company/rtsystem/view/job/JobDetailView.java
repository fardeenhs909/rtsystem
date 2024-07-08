package com.company.rtsystem.view.job;

import com.company.rtsystem.entity.Job;

import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "jobs/:id", layout = MainViewTopMenu.class)
@ViewController("Job.detail")
@ViewDescriptor("job-detail-view.xml")
@EditedEntityContainer("jobDc")
public class JobDetailView extends StandardDetailView<Job> {
}