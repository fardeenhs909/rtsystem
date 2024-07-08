package com.company.rtsystem.view.job;

import com.company.rtsystem.entity.Job;

import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "jobs", layout = MainViewTopMenu.class)
@ViewController("Job.list")
@ViewDescriptor("job-list-view.xml")
@LookupComponent("jobsDataGrid")
@DialogMode(width = "64em")
public class JobListView extends StandardListView<Job> {
}