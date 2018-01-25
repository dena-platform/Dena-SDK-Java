package com.dena.client.service;

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

}
