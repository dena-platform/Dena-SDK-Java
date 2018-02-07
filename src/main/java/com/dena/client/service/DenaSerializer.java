package com.dena.client.service;

import com.dena.client.utils.DenaClassUtils;
import com.dena.client.utils.DenaCollectionUtils;
import com.dena.client.utils.DenaMapUtils;
import com.dena.client.utils.DenaReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dena.client.Dena.DENA_OBJECT_ID_FIELD;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class DenaSerializer {
    private final static Logger log = LoggerFactory.getLogger(DenaSerializer.class);


    public Map<String, Object> serilizeObject(Object targetObject) {
        // todo: can use object_id instead of denaObject_id
        final String objectId = "object_id";
        Map<String, Object> fields = findAllFields(targetObject);
        Map<String, Object> refinedFields = new HashMap<>();

        // replace denaObjectId with objectId
        Object previousValue = fields.remove(DENA_OBJECT_ID_FIELD);
        if (previousValue != null) {
            fields.put(objectId, previousValue);
        }

        // ignore null value fields or empty collection, map
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            if (entry.getValue() == null) {
                // ignore null
                continue;
            }

            if (entry.getValue() instanceof Collection && ((Collection) entry.getValue()).size() == 0) {
                // ignore empty collection
                continue;
            }

            if (entry.getValue() instanceof Collection &&
                    !DenaClassUtils.isPrimitiveOrWrapperCollection((Collection) entry.getValue())) {
                // ignore collection that contain non primitive or non-wrapper element
                continue;

            }

            if (entry.getValue() instanceof Map) {
                // ignore map
                continue;
            }

            refinedFields.put(entry.getKey(), entry.getValue());
        }

        return refinedFields;
    }


    public Map<String, Object> findAllFields(Object targetObject) {
        List<Field> fieldList = DenaReflectionUtils.findInstanceVariables(targetObject);
        Map<String, Method> getterMethodList = DenaReflectionUtils.findGetterMethods(targetObject);

        Map<String, Object> returnMap = new HashMap<>();

        // call getter method
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

        // find public field
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

}
