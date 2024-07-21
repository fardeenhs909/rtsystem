package com.company.rtsystem.service.reference;

import com.company.rtsystem.entity.ReferenceValue;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;

@Component
public class ReferenceDataService {
    private static final String TABLE_NAME = "tableName";
    @Autowired
    private final DataManager dataManager;

    public ReferenceDataService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void createCode(Id<ReferenceValue> referenceValueId){
        ReferenceValue value = dataManager.load(referenceValueId).fetchPlan("_base").one();
        if (value == null) {
            throw new IllegalArgumentException("No entity with the provided ID exists.");
        }
        String name = value.getDisplay();

        
        if(value.getCode() == null){
            value.setCode("code." + name.replaceAll("\\W", "").toLowerCase());
        }
        if(value.getStartDate() == null){
            value.setStartDate(new Date());
        }
        dataManager.save(value);
    }

    public Map<ReferenceValue, String> getReferenceValues(String tableName, @Nullable ReferenceValue existingValue){
        Map<ReferenceValue, String> valuesMap = new LinkedHashMap<>();
        dataManager.load(ReferenceValue.class)
                .query("select e from ReferenceValue e where e.referenceTable.tableName = :tableName and ((e.endDate is null or e.endDate >= :now) " +
                        " and (e.startDate is null or e.startDate <= :now)) or e =:existingValue order by e.order, e.display")
                .parameter(TABLE_NAME, tableName)
                .parameter("existingValue", existingValue)
                .parameter("now", new Date())
                .list().forEach(value -> valuesMap.put(value, value.getDisplay()));
        return valuesMap;

    }

    public List<ReferenceValue> getReferenceValuesList(String tableName, @Nullable Set<ReferenceValue> existingValues) {
        if (existingValues == null) {
            existingValues = Collections.emptySet();
        }

        return dataManager.load(ReferenceValue.class)
                .query("select e from ReferenceValue e where e.referenceTable.tableName = :tableName and ((e.endDate is null or e.endDate >= :now) " +
                        "and (e.startDate is null or e.startDate <= :now)) or (e in :existingValues) order by e.order, e.display")
                .parameter(TABLE_NAME, tableName)
                .parameter("existingValues", existingValues)
                .parameter("now", new Date())
                .list();
    }

    public ReferenceValue getValueByCode(String tableName, String code){
        return dataManager.load(ReferenceValue.class)
                .query("select e from ReferenceValue e where e.referenceTable.tableName = :tableName and e.code = :code")
                .parameter("code", code)
                .parameter(TABLE_NAME, tableName)
                .optional().orElse(null);
    }

    public ReferenceValue getValueWithMaxOrder(String tableName) {
        List<ReferenceValue> values = getReferenceValuesList(tableName, null);
        Optional<ReferenceValue> maxOrderReferenceValue = values.stream()
                .max(Comparator.comparing(ReferenceValue::getOrder));
        return maxOrderReferenceValue.orElse(values.get(0));
    }
}
