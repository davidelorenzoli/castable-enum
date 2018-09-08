package com.dlorenzo.util;

import java.util.Optional;

public class ClassUtils {
    public static <T> Optional<T> newInstance(Class<T> type, String value) {
        Optional<T> result = Optional.empty();

        try {
            T instance = type.getConstructor(value.getClass()).newInstance(value);
            result = Optional.ofNullable(instance);
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return result;
    }
}
