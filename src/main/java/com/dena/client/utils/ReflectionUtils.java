package com.dena.client.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static java.lang.reflect.Modifier.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class ReflectionUtils {
    private final static String GETTER_PREFIX = "get";
    private final static String BOOLEAN_GETTER_PREFIX = "is";
    private final static String SETTER_PREFIX = "set";

    /**
     * Find all non-static, non-transient public fields in class or inherited classes.
     *
     * @param object object for searching to find field
     * @return
     */
    public static List<Field> findInstanceVariables(Object object) {
        Class klass = object.getClass();
        List<Field> fieldList = new LinkedList<>();

        while (klass != null) {
            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                int modifier = field.getModifiers();
                if (isPublic(modifier) && !isStatic(modifier) && !isTransient(modifier)) {
                    fieldList.add(field);
                }
            }

            klass = klass.getSuperclass();
        }
        return fieldList;
    }

    /**
     * Find all non-static, non-transient public fields in class or inherited classes.
     *
     * @param object object for searching to find method
     * @return
     */

    public static List<Method> findGetterSetterMethod(Object object) {
        Class klass = object.getClass();
        List<Method> methodList = new LinkedList<>();
        while (klass != null) {
            Method[] methods = klass.getDeclaredMethods();
            for (Method method : methods) {
                if (isGetterMethod(method) && isSetterMethodExist(methods, method)) {
                    methodList.add(method);
                }
            }
            klass = klass.getSuperclass();
        }
        return methodList;

    }

    private static boolean isSetterMethodExist(Method[] methods, Method getterMethod) {
        String methodNameWithoutPrefix = excludePrefixFromMethodName(getterMethod.getName());
        Class<?> returnType = getterMethod.getReturnType();

        for (Method method : methods) {
            if (isSetterMethod(method, returnType) && method.getName().contains(methodNameWithoutPrefix)) {
                return true;
            }
        }
        return false;
    }


    private static boolean isGetterMethod(Method method) {
        String methodName = method.getName();
        int modifiers = method.getModifiers();

        if (!isPublic(modifiers) || isStatic(modifiers)) return false;

        if (!methodName.startsWith(GETTER_PREFIX) || !methodName.startsWith(BOOLEAN_GETTER_PREFIX)) {
            return false;
        }

        if (method.getParameters().length != 0) return false;

        if (method.getReturnType().equals(void.class)) return false;

        return true;
    }

    private static boolean isSetterMethod(Method method) {
        String methodName = method.getName();
        int modifiers = method.getModifiers();

        if (!isPublic(modifiers) || isStatic(modifiers)) return false;

        if (!methodName.startsWith(SETTER_PREFIX)) {
            return false;
        }

        if (method.getParameters().length != 1) return false;

        return true;

    }

    private static boolean isSetterMethod(Method method, Class<?> parameterType) {
        if (!isSetterMethod(method)) return false;

        if (!method.getParameters()[0].getType().equals(parameterType)) return false;

        return true;

    }


    private static String excludePrefixFromMethodName(final String methodName) {
        if (methodName.startsWith(GETTER_PREFIX)) {
            return methodName.substring(GETTER_PREFIX.length());
        } else if (methodName.startsWith(BOOLEAN_GETTER_PREFIX)) {
            return methodName.substring(BOOLEAN_GETTER_PREFIX.length());
        } else if (methodName.startsWith(SETTER_PREFIX)) {
            return methodName.substring(SETTER_PREFIX.length());
        } else {
            return methodName;
        }

    }

}
