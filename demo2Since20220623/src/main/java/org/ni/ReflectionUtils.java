package org.ni;

import java.lang.reflect.Method;
import java.util.Optional;

public class ReflectionUtils {

    public static Optional<Method> getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {

        Optional<Method> method = Optional.empty();
        Method m;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                m = clazz.getDeclaredMethod(methodName, parameterTypes);
                m.setAccessible(true);
                return Optional.of(m);
            } catch (NoSuchMethodException e) {
                // do nothing
            }
        }

        return method;
    }

}