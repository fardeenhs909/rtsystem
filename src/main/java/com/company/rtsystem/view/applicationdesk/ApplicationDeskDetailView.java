package com.company.rtsystem.view.applicationdesk;

import com.company.rtsystem.entity.ApplicationDesk;

import com.company.rtsystem.entity.ReferenceValue;
import com.company.rtsystem.service.reference.ReferenceTables;
import com.company.rtsystem.ui.CommonUIUtils;
import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Route(value = "applicationDesks/:id", layout = MainViewTopMenu.class)
@ViewController("ApplicationDesk.detail")
@ViewDescriptor("application-desk-detail-view.xml")
@EditedEntityContainer("applicationDeskDc")
public class ApplicationDeskDetailView extends StandardDetailView<ApplicationDesk> {

    @ViewComponent
    private EntityComboBox<ReferenceValue> experienceField;
    @Autowired
    private CommonUIUtils commonUIUtils;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event){
        loadReferenceData();
    }

    private void loadReferenceData(){

        Map<Component, String> values = new HashMap<>();
        values.put(experienceField, ReferenceTables.EXPERIENCE);
        loadReferenceDataCB(commonUIUtils, values);
    }

    public void loadReferenceDataCB(CommonUIUtils UI, Map<Component, String> fields) {
        for (Component component : fields.keySet()) {
            if (component instanceof ComboBox<?>) {
                UI.load(fields.get(component), (ComboBox<ReferenceValue>) component);
            } else if (component instanceof MultiSelectComboBox<?>) {
                UI.load(fields.get(component), (MultiSelectComboBox<ReferenceValue>) component);
            } else if (component instanceof JmixRadioButtonGroup<?>) {
                UI.load(fields.get(component), (JmixRadioButtonGroup<ReferenceValue>) component);
            }
        }
    }
}