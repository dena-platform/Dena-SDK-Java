package com.dena.client.service;

import com.dena.client.exception.DenaFault;
import com.dena.client.utils.DenaReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

import static com.dena.client.service.DenaSerializer.DENA_OBJECT_ID_FIELD;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaMapperImpl implements DenaMapper {
    private final static Logger log = LoggerFactory.getLogger(DenaMapperImpl.class);

    private DenaSerializer denaSerializer = new DenaSerializer();

    @Override
    public String findTypeName(Object targetObject) {
        String fullClassName = DenaReflectionUtils.findClassName(targetObject);
        int endIndex = !fullClassName.contains(DenaReflectionUtils.CLASS_NAMING_PREFIX) ?
                fullClassName.length() : fullClassName.indexOf(DenaReflectionUtils.CLASS_NAMING_PREFIX);
        return fullClassName.substring(0, endIndex);
    }

    @Override
    public <T> boolean isObjectIdSet(T targetObject) {
        return findAllFields(targetObject).containsKey(DENA_OBJECT_ID_FIELD) &&
                findAllFields(targetObject).get(DENA_OBJECT_ID_FIELD) != null;
    }

    /**
     * Inject field with name 'denaObjectId' and specified value to target object.
     * if field with that name exist then only set value to field.
     *
     * @param targetObject
     * @param objectId
     * @param <T>
     * @return
     */
    @Override
    public <T> T setObjectId(final T targetObject, final String objectId) {

        try {
            if (findAllFields(targetObject).containsKey(DENA_OBJECT_ID_FIELD)) {
                DenaReflectionUtils.forceSetField(targetObject, DENA_OBJECT_ID_FIELD, objectId);
                return targetObject;
            } else {
                Class<T> newClass = (Class<T>) DenaReflectionUtils.injectPublicFieldToClass(targetObject.getClass(), String.class, DENA_OBJECT_ID_FIELD);
                T newObject = DenaReflectionUtils.callDefaultConstructor(newClass);
                DenaReflectionUtils.copyObject(targetObject, newObject);
                DenaReflectionUtils.forceSetField(newObject, DENA_OBJECT_ID_FIELD, objectId);

                return newObject;
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            throw new DenaFault(String.format("Can not set objectId [%s]", objectId), ex);
        }

    }
}
