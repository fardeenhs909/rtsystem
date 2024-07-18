package com.company.rtsystem.view.referencevalue;

import com.company.rtsystem.entity.ReferenceValue;

import com.company.rtsystem.ui.DuplicateValidator;
import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;

import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Metadata;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "referenceValues/:id", layout = MainViewTopMenu.class)
@ViewController("ReferenceValue.detail")
@ViewDescriptor("reference-value-detail-view.xml")
@EditedEntityContainer("referenceValueDc")
public class ReferenceValueDetailView extends StandardDetailView<ReferenceValue> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Metadata metadata;
    @ViewComponent
    private TypedTextField<Object> displayField;
    @Autowired
    private DuplicateValidator duplicateValidator;
    @Autowired
    private EntityStates entityStates;

    @Install(to = "displayField", subject = "validator")
    private void displayFieldValidator(final Object value) {
        duplicateValidator.isDuplicateRefDataEntry(getEditedEntity().getReferenceTable().getTableName(),
                displayField.getValue(),
                getEditedEntity().getId());
    }
}