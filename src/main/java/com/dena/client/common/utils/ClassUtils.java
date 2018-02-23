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

    /**
     * Find original class name of targetObject without added redundant 
     * @param targetObject
     * @return
     */
    public static String findOriginalTypeName(final Object targetObject) {
        return ReflectionUtils.findClassNameWithoutRedundant(ReflectionUtils.findClassName(targetObject));
    }

    public static boolean isCollectionType(final Object targetObject) {
        return targetObject != null && (targetObject instanceof Collection);
    }

    public static boolean isCollectionOfSameType(Collection<?> denaObjects) {
        if (CollectionUtils.isEmpty(denaObjects) || denaObjects.size() == 1) {
            return true;
        }

        Object firstObject = denaObjects.iterator().next();
        for (Object otherObject : denaObjects) {
            if (!findOriginalTypeName(firstObject).equals(findOriginalTypeName(otherObject))) {
                return false;
            }
        }

        return true;
    }


}
