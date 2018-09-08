package com.dlorenzo.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface CastableEnum {
    /**
     * The function that returns the value associated to an enumeration key, e.g. for the
     * JVMArg enum key USERNAME defined as USERNAME("user.name") it should return
     * System.getProperty("user.name")
     *
     * @return the value associated with an enumeration key
     */
    Function<String, String> getMappingFunction();

    /**
     * Get the enumeration key item value, e.g. for the JVMArgs enum key USERNAME defined
     * as USERNAME("user.name") it should return "user.name"
     *
     * @return the enumeration key item value
     */
    String getKey();

    /**
     * @return the {@link String} representation of the value returned by the
     * mapping-function, null if the value cannot be represented as {@link String} object
     */
    default String asString() {
        return as(String.class).orElse(null);
    }

    /**
     * @return the {@link Number} representation of the value returned by the
     * mapping-function, null if the value cannot be represented as {@link Number} object
     */
    default Number asNumber() {
        return as(Integer.class).orElse(null);
    }

    /**
     * @return the {@link URL} representation of the value returned by the
     * mapping-function, null if the value cannot be represented as {@link URL} object
     */
    default URL asURL() {
        return as(URL.class).orElse(null);
    }

    /**
     * @return the {@link Boolean} representation of the value returned by the
     * mapping-function, null if the value cannot be represented as {@link Boolean}
     * object
     */
    default Boolean asBoolean() {
        return as(Boolean.class).orElse(null);
    }

    /**
     * Get the {@link List} representation of the value returned by the mapping-function.
     * The mapping-function return value is converted to a {@link String} (see {@link
     * CastableEnum#asString()}, then the string is split according to the given
     * delimiter. Each so obtained element is then converted to the given array-ype. The
     * element that fails to be converted to array-type is omitted.
     *
     * @return the {@link List} representation of the value returned by the
     * mapping-function, null if the value cannot be represented as {@link List} object
     */
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

    /**
     * @param returnType
     *         the type to convert he value returned by the mapping-function to
     *
     * @return the given return-type representation of the value returned by the
     * mapping-function, never null
     */
    default <T> Optional<T> as(Class<T> returnType) {
        Optional<T> result = Optional.empty();
        String value = getMappingFunction().apply(getKey());

        if (value != null) {
            result = ClassUtils.newInstance(returnType, value);
        }

        return result;
    }
}
