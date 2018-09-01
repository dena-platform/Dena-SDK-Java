package com.dena.client.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static com.dena.client.common.utils.ClassUtils.isStringOrPrimitiveType;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public final class CollectionUtils {
    public static boolean isEmpty(Collection<?> collections) {
        return collections == null || collections.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collections) {
        return !isEmpty(collections);
    }

    /**
     * Check if all element of collection is primitive or wrapper of primitive
     *
     * @param collection
     * @return
     */
    public static boolean isPrimitiveCollection(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Object element = iterator.next();

            if (!isStringOrPrimitiveType(element.getClass())) {
                return false;
            }
        }

        return true;
    }

    /**
     * check if collection contain only String type
     *
     * @param collection
     * @return
     */
    public static boolean isStringCollection(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Object element = iterator.next();

            if (!element.getClass().equals(String.class)) {
                return false;
            }

            return true;
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
            if (!isStringOrPrimitiveType(entry.getValue().getClass())) {
                return false;
            }
        }

        return true;
    }



}
