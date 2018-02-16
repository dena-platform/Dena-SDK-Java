package com.dena.client.common.utils;

import com.dena.client.core.feature.persistence.Relation;

import java.util.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class ClassUtils {
    private final static Set<Class<?>> PRIMITIVE_WRAPPER_CLASS = new HashSet<>();

    static {
        PRIMITIVE_WRAPPER_CLASS.add(Boolean.class);
        PRIMITIVE_WRAPPER_CLASS.add(Byte.class);
        PRIMITIVE_WRAPPER_CLASS.add(Character.class);
        PRIMITIVE_WRAPPER_CLASS.add(Short.class);
        PRIMITIVE_WRAPPER_CLASS.add(Integer.class);
        PRIMITIVE_WRAPPER_CLASS.add(Long.class);
        PRIMITIVE_WRAPPER_CLASS.add(Double.class);
        PRIMITIVE_WRAPPER_CLASS.add(Float.class);
        PRIMITIVE_WRAPPER_CLASS.add(Void.class);
    }


    public static boolean isPrimitiveType(final Object targetObject) {
        return targetObject.getClass().isPrimitive() || PRIMITIVE_WRAPPER_CLASS.contains(targetObject.getClass());
    }

    public static boolean isRelationType(final Object targetObject) {
        return targetObject instanceof Relation;
    }


    public static String findSimpleTypeName(final Object targetObject) {
        return ReflectionUtils.findTypeName(ReflectionUtils.findClassName(targetObject));
    }

    public static boolean isCollectionType(final Object targetObject) {
        return targetObject != null && (targetObject instanceof Collection);
    }



}
