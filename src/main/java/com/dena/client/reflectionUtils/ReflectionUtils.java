package com.dena.client.reflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class ReflectionUtils {
    /**
     * Find all public fields in class or inherited class (not interface)
     *
     * @param object
     * @return
     */
    public static List<Field> findAllFields(Object object) {
        Class klass = object.getClass();
        List<Field> fieldList = new LinkedList<>();

        while (klass != null) {
            Field[] fields = klass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            klass = klass.getSuperclass();
        }
        return fieldList;
    }

}
