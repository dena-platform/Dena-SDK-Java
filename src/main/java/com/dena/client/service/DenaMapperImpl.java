package com.dena.client.service;

import com.dena.client.utils.DenaCollectionUtils;
import com.dena.client.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
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
        List<Field> fieldList = ReflectionUtils.findInstanceVariables(object);
        List<Method> methodList = ReflectionUtils.findGetterSetterMethod(object);

        Map<String, Object> returnMap = new HashMap<>();

        if (DenaCollectionUtils.isNotEmpty(methodList)) {
            for (Method method : methodList) {
                try {
                    returnMap.put(field.getName(), field.get(object));
                } catch (IllegalAccessException ex) {
                    log.error("Error in getting value ", ex);
                }
            }
            return returnMap;

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
