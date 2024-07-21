package com.company.rtsystem.ui;

import com.company.rtsystem.entity.ReferenceValue;
import com.company.rtsystem.service.reference.ReferenceDataService;
import com.company.rtsystem.service.reference.ReferenceValues;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.kit.component.ComponentUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class CommonUIUtils {

    private final ReferenceDataService referenceDataService;
    public CommonUIUtils(ReferenceDataService referenceDataService){
        this.referenceDataService = referenceDataService;
    }

    public void load(String refValueName, ComboBox<ReferenceValue> field){
        ReferenceValue referenceValue = field.getValue();
        Map<ReferenceValue, String> map = referenceDataService.getReferenceValues(refValueName, referenceValue);
        ComponentUtils.setItemsMap(field, map);
        field.setValue(referenceValue);
    }

    public void load(String refValueName, MultiSelectComboBox<ReferenceValue> field){
        Set<ReferenceValue> referenceValue = field.getValue();
        field.setItems(referenceDataService.getReferenceValuesList(refValueName, referenceValue));
        field.setValue(referenceValue);
    }

    public void load(String refValueName, JmixRadioButtonGroup<ReferenceValue> field) {
        ReferenceValue existingValue = field.getValue();
        Map<ReferenceValue, String> map = referenceDataService.getReferenceValues(
                refValueName, existingValue
        );
        ComponentUtils.setItemsMap(field, map);
        field.setValue(existingValue);
    }
}
