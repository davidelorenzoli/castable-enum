package com.dlorenzo.util;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface CastableEnum {
    Function<String, String> getValueFunction();

    String getKey();

    default String asString() {
        return as(String.class).orElse(null);
    }

    default Number asNumber() {
        return as(Integer.class).orElse(null);
    }

    default URL asURL() {
        return as(URL.class).orElse(null);
    }

    default <T> List<T> asList(String delimiter, Class<T> arrayType) {
        String value = asString();
        List<T> list = new ArrayList<>();

        if (value != null) {
            list = Arrays.stream(value.split(delimiter))
                    .map(item -> ClassUtils.newInstance(arrayType, item).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return list;
    }

    default <T> Optional<T> as(Class<T> returnType) {
        Optional<T> result = Optional.empty();
        String value = getValueFunction().apply(getKey());

        if (value != null) {
            result = ClassUtils.newInstance(returnType, value);
        }

        return result;
    }
}
