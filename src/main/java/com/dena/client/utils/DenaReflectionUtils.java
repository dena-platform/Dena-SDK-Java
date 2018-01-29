package com.dena.client.utils;

import net.bytebuddy.ByteBuddy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;

import static java.lang.reflect.Modifier.*;
import static java.lang.reflect.Modifier.isPublic;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class DenaReflectionUtils {
    private final static Logger log = LoggerFactory.getLogger(DenaReflectionUtils.class);

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


    /**
     * Add public field to class.
     *
     * @param targetClass target class that we want to add field
     * @param type        type of new field
     * @param name        name of new field
     * @return new object with added field
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> Class<? extends T> injectPublicFieldToClass(Class<T> targetClass, Type type, String name) {
        Class<? extends T> newClass = new ByteBuddy()
                .subclass(targetClass)
                .defineField(name, type, Modifier.PUBLIC)
                .make()
                .load(DenaReflectionUtils.class.getClassLoader())
                .getLoaded();

        return newClass;
    }

    /**
     * Call default constructor of specified class.
     *
     * @param klass
     * @param <T>
     * @return  new object of specified class.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> T callDefaultConstructor(Class<T> klass) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<T> constructor = klass.getConstructor();
        return constructor.newInstance();
    }

    /**
     * Copy all fields of source to destination object.
     * @param sourceObject
     * @param destinationObject
     * @param <T>
     * @return
     */
    public static <T> T copyObject(T sourceObject, T destinationObject) {
        if (sourceObject == null || destinationObject == null) {
            throw new NullPointerException("source or destination object is null");
        }

        Class sourceKlass = sourceObject.getClass();
        while (sourceKlass != null) {
            Field[] sourceFields = sourceKlass.getDeclaredFields();
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                try {
                    sourceField.set(destinationObject, sourceField.get(sourceObject));
                } catch (IllegalAccessException ex) {
                    log.error("Error in setting field", ex);
                }
            }

            sourceKlass = sourceKlass.getSuperclass();
        }
        return destinationObject;

    }

    /**
     * Set field of target object to specified value even if that field is not public.
     * @param targetObject
     * @param fieldName
     * @param value
     * @throws IllegalAccessException
     */
    public static void forceSetField(Object targetObject, String fieldName, Object value) throws IllegalAccessException {
        Class<?> klass = targetObject.getClass();
        Optional<Field> field = findField(klass, fieldName, true);
        if (field.isPresent()) {
            field.get().set(targetObject, value);
        } else {
            throw new RuntimeException(String.format("Can not find field [%s] in class [%s]", field, klass));
        }
    }

    private static Optional<Field> findField(Class<?> klass, String fieldName, boolean forceAccess) {
        while (klass != null) {
            try {
                Field field = klass.getDeclaredField(fieldName);
                if (!isPublic(field.getModifiers())) {
                    if (forceAccess) {
                        field.setAccessible(true);
                    } else {
                        continue;
                    }
                }
                return Optional.of(field);
            } catch (NoSuchFieldException e) {
                // do nothing
            }

            klass = klass.getSuperclass();
        }

        return Optional.empty();
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
