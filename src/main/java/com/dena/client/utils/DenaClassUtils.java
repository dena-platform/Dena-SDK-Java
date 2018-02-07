package com.dena.client.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class DenaClassUtils {
    private final static Set<Class<?>> PRIMITIVE_WRAPPER_CLASS = new HashSet<>();

    public static boolean isPerimitiveOrWrapper(final Class<?> klass) {
        return klass.isPrimitive() || PRIMITIVE_WRAPPER_CLASS.contains(klass);
    }

    static {
        PRIMITIVE_WRAPPER_CLASS.add(Boolean.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Byte.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Character.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Short.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Integer.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Long.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Double.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Float.TYPE);
        PRIMITIVE_WRAPPER_CLASS.add(Void.TYPE);
    }
}
