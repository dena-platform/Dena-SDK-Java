package com.dena.client.utils;

import net.bytebuddy.ByteBuddy;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

import static java.lang.reflect.Modifier.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class DenaReflectionUtils {
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

    //todo: check for non static field

    /**
     * Find all non-static, non-transient getter method in the object.
     *
     * @param object object for searching to find getter method
     * @return
     */

    public static Map<String, Method> findGetterMethods(Object object) {
        Class klass = object.getClass();
        Map<String, Method> getterMethod = new HashMap<>();

        for (PropertyDescriptor propertyDescriptor : findGetterMethods(klass)) {
            getterMethod.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod());
        }

        return getterMethod;
    }

    /**
     * Find simple class name of object.
     *
     * @param object object for searching to find class name.
     * @return
     */
    public static String findClassName(Object object) {
        String typeName = object.getClass().getSimpleName();
        return typeName;
    }

    public static void addPublicFieldToObject(Object object, Type type, String name, Object value) throws IllegalAccessException, InstantiationException {
        Class<?> parentClass = object.getClass();
        Object newObject = new ByteBuddy().
                subclass(parentClass)
                .defineField(name, type, Modifier.PUBLIC)
                .make()
                .load(parentClass.getClass().getClassLoader())
                .getLoaded()
                .newInstance();

        
    }

    private static List<PropertyDescriptor> findGetterMethods(Class<?> klass) {
        List<PropertyDescriptor> propertyDescriptorList = new ArrayList<>();
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(klass).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                    propertyDescriptorList.add(pd);
                }
            }
        } catch (IntrospectionException e) {
            // do-nothing

        }

        return propertyDescriptorList;
    }


}
