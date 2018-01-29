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
    public Map<String, Object> findAllFields(Object object) {
        List<Field> fieldList = DenaReflectionUtils.findInstanceVariables(object);
        Map<String, Method> getterMethodList = DenaReflectionUtils.findGetterMethods(object);

        Map<String, Object> returnMap = new HashMap<>();

        if (DenaMapUtils.isNotEmpty(getterMethodList)) {
            for (Map.Entry<String, Method> method : getterMethodList.entrySet()) {
                try {
                    String propertyName = method.getKey();
                    returnMap.put(propertyName, method.getValue().invoke(object));
                } catch (InvocationTargetException | IllegalAccessException ex) {
                    log.error("Error in getting value ", ex);
                }
            }
        }

        if (DenaCollectionUtils.isNotEmpty(fieldList)) {
            for (Field field : fieldList) {
                try {
                    returnMap.put(field.getName(), field.get(object));
                } catch (IllegalAccessException ex) {
                    log.error("Error in getting value ", ex);
                }
            }
        }

        return returnMap;
    }

    @Override
    public String findTypeName(Object object) {
        String typeName = DenaReflectionUtils.findClassName(object);
        return typeName;
    }

    /**
     * Inject 'denaObjectId' for specified target object. if 'denaObjectId' exist then do nothing.
     * @param targetObject
     * @param objectId
     * @param <T>
     * @return
     */
    @Override
    public <T> T setObjectId(final T targetObject, final String objectId) {
        try {
            Class<T> newClass = (Class<T>) DenaReflectionUtils.injectPublicFieldToClass(targetObject.getClass(), String.class, DENA_OBJECT_ID);
            T newObject = DenaReflectionUtils.callDefaultConstructor(newClass);
            DenaReflectionUtils.copyObject(targetObject, newObject);
            DenaReflectionUtils.forceSetField(newObject, DENA_OBJECT_ID, objectId);

            return newObject;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            throw new DenaFault(String.format("Can not set objectId [%s]", objectId), ex);
        }
    }

}
