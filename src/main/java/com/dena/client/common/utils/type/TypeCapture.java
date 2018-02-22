package com.dena.client.common.utils.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
abstract class TypeCapture<T> {
    final Type findType() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[0];
        }

        return null;

    }
}
