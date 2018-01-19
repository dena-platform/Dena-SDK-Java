package com.dena.client.service;

import com.dena.client.utils.ReflectionUtils;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class MapperImpl implements Mapper {
    private final static Logger log = LoggerFactory.getLogger(MapperImpl.class);

    @Override
    public Map<String, Object> findAllFields(Object object) {
        List<Field> fieldList = ReflectionUtils.findAllInstanceVariables(object);
        if (fieldList != null && !fieldList.isEmpty()) {
            Map<String, Object> returnMap = new HashMap<>();
            for (Field field : fieldList) {
                try {
                    returnMap.put(field.getName(), field.get(object));
                } catch (IllegalAccessException ex) {
                    log.error("Error in getting value ", ex);
                }
            }
            return returnMap;
        }

        return Collections.emptyMap();
    }
}
