package com.dena.client.service;

import com.dena.client.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class MapperImpl implements Mapper {
    @Override
    public Map<String, Object> findAllFields(Object object) {
        List<Field> fieldList = ReflectionUtils.findAllInstanceVariables(object);

        
        return null;
    }
}
