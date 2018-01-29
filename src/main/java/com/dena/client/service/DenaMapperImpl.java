package com.dena.client.service;

import com.dena.client.exception.DenaFault;
import com.dena.client.utils.DenaCollectionUtils;
import com.dena.client.utils.DenaMapUtils;
import com.dena.client.utils.DenaReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaMapperImpl implements DenaMapper {
    private final static Logger log = LoggerFactory.getLogger(DenaMapperImpl.class);

    public static final String DENA_OBJECT_ID = "denaObjectId";

    @Override
    public Map<String, Object> findAllFields(Object targetObject) {
        List<Field> fieldList = DenaReflectionUtils.findInstanceVariables(targetObject);
        Map<String, Method> getterMethodList = DenaReflectionUtils.findGetterMethods(targetObject);

        Map<String, Object> returnMap = new HashMap<>();

        if (DenaMapUtils.isNotEmpty(getterMethodList)) {
            for (Map.Entry<String, Method> method : getterMethodList.entrySet()) {
                try {
                    String propertyName = method.getKey();
                    returnMap.put(propertyName, method.getValue().invoke(targetObject));
                } catch (InvocationTargetException | IllegalAccessException ex) {
                    log.error("Error in getting value ", ex);
                }
            }
        }

        if (DenaCollectionUtils.isNotEmpty(fieldList)) {
            for (Field field : fieldList) {
                try {
                    returnMap.put(field.getName(), field.get(targetObject));
                } catch (IllegalAccessException ex) {
                    log.error("Error in getting value ", ex);
                }
            }
        }

        return returnMap;
    }

    @Override
    public String findTypeName(Object targetObject) {
        return DenaReflectionUtils.findClassName(targetObject);
    }

    @Override
    public <T> boolean isObjectIdSet(T targetObject) {
        return findAllFields(targetObject).containsKey(DENA_OBJECT_ID) && findAllFields(targetObject).get(DENA_OBJECT_ID) != null;
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
            if (findAllFields(targetObject).containsKey(DENA_OBJECT_ID)) {
                DenaReflectionUtils.forceSetField(targetObject, DENA_OBJECT_ID, objectId);
                return targetObject;
            } else {
                Class<T> newClass = (Class<T>) DenaReflectionUtils.injectPublicFieldToClass(targetObject.getClass(), String.class, DENA_OBJECT_ID);
                T newObject = DenaReflectionUtils.callDefaultConstructor(newClass);
                DenaReflectionUtils.copyObject(targetObject, newObject);
                DenaReflectionUtils.forceSetField(newObject, DENA_OBJECT_ID, objectId);

                return newObject;
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            throw new DenaFault(String.format("Can not set objectId [%s]", objectId), ex);
        }

    }

}
