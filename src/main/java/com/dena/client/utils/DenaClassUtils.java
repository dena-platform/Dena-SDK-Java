package com.dena.client.utils;

import java.util.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class DenaClassUtils {
    private final static Set<Class<?>> PRIMITIVE_WRAPPER_CLASS = new HashSet<>();

    public static boolean isPrimitiveOrWrapper(final Class<?> klass) {
        return klass.isPrimitive() || PRIMITIVE_WRAPPER_CLASS.contains(klass);
    }

    /**
     * check if all element of Collection is primitive or wrapper of primitive
     *
     * @param collection
     * @return
     */
    public static boolean isPrimitiveOrWrapperCollection(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Object element = iterator.next();
            if (!isPrimitiveOrWrapper(element.getClass())) {
                return false;
            }
        }

        return true;
    }

    /**
     * check if value element of Map is primitive or wrapper of primitive
     *
     * @param map
     * @return
     */

    public static boolean isPrimitiveOrWrapperMapValue(Map<String, ?> map) {
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (!isPrimitiveOrWrapper(entry.getValue().getClass())) {
                return false;
            }
        }

        return true;
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
