package com.dlorenzo.util;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassUtils {
    /**
     * Uses the constructor represented by the given type Constructor object to create and
     * initialize a new instance of the constructor's declaring class, with the specified
     * initialization parameters.
     *
     * @param type
     *         the type to create an instance for
     * @param initArgs
     *         the object initialization parameters
     * @param <T>
     *
     * @return the created instance
     */
    public static <T> Optional<T> newInstance(Class<T> type, Object... initArgs) {
        Optional<T> result = Optional.empty();

        try {
            Class[] initArgTypes = Arrays.stream(initArgs)
                    .filter(Objects::nonNull)
                    .map(initArg -> initArg.getClass())
                    .collect(Collectors.toList())
                    .toArray(new Class[0]);

            Constructor<T> constructor = type.getConstructor(initArgTypes);
            T instance = constructor.newInstance(initArgs);

            result = Optional.ofNullable(instance);
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return result;
    }
}
