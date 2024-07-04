package com.company.rtsystem.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;


public enum BannerStyle implements EnumClass<String> {

    WARN("WARN"),
    INFO("INFO");

    private final String id;

    BannerStyle(String id) {
        this.id = id;
    }

    @Nullable
    public static BannerStyle fromId(String id) {
        for (BannerStyle at : BannerStyle.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
