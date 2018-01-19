package com.dena.client.utils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static java.lang.reflect.Modifier.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class ReflectionUtils {
    /**
     * Find all non-static, non-transient public fields in class or inherited classes.
     *
     * @param object
     * @return
     */
    public static List<Field> findAllInstanceVariables(Object object) {
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


}
