package com.company.rtsystem.ui;

import com.company.rtsystem.entity.ReferenceValue;
import com.company.rtsystem.service.reference.ReferenceDataService;
import io.jmix.flowui.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class DuplicateValidator<T> {

    private final ReferenceDataService referenceDataService;

    public DuplicateValidator(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    public void isDuplicateRefDataEntry(String tableName, String display, UUID id) {
        if (display.trim().length() < 1) {
            throw new ValidationException("The entered value is invalid");
        }
        Map<ReferenceValue, String> values = referenceDataService.getReferenceValues(tableName, null);
        values.entrySet().stream()
                .filter(e -> e.getValue().equals(display) && !e.getKey().getId().equals(id))
                .findFirst()
                .ifPresent(e -> {
                    throw new ValidationException(String.format("Duplicate Value %s within Reference Table %s is not allowed.",
                            display, tableName));
                });
    }
}
