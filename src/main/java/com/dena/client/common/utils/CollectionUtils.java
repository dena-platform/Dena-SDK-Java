package com.dena.client.common.utils;

import java.util.Collection;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public final class CollectionUtils {
    public static boolean isEmpty(Collection<?> collections) {
        return collections == null || collections.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collections) {
        return !isEmpty(collections);
    }


}
