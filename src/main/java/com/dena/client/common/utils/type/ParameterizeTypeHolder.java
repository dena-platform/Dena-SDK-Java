package com.dena.client.common.utils.type;

import java.lang.reflect.Type;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public abstract class ParameterizeTypeHolder<T> extends TypeCapture<T> {
    private Type type;

    protected ParameterizeTypeHolder() {
        this.type = findType();
    }

    public String getParameterizeTypeName() {
        return type.getTypeName();
    }
}
