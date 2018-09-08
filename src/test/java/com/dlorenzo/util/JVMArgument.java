package com.dlorenzo.util;

import java.util.function.Function;

public enum JVMArgument implements CastableEnum {
    SOURCE_ID("sourceId"),
    WEB_SERVER("webServer"),
    FEATURES("features");

    private final String key;

    JVMArgument(String key) {
        this.key = key;
    }

    @Override
    public Function<String, String> getDataFunction() {
        return key -> System.getProperty(key);
    }

    @Override
    public String getKey() {
        return key;
    }
}