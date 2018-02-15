package com.dena.client.core.feature.persistence;

import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.utils.ClassUtils;
import com.dena.client.common.utils.CollectionUtils;
import com.dena.client.common.utils.MapUtils;
import com.dena.client.common.utils.ReflectionUtils;
import com.dena.client.core.feature.persistence.dto.RelatedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class DenaSerializer {
    public final static String DENA_OBJECT_ID_FIELD = "object_id";
    public final static String RELATED_OBJECT_FIELD = "related_objects";

    private final static Logger log = LoggerFactory.getLogger(DenaSerializer.class);

    @SuppressWarnings("unchecked")
    public static Map<String, Object> serializeToMap(Object targetObject) {
        Map<String, Object> fields = findAllFields(targetObject);
        Map<String, Object> refinedFields = new HashMap<>();

        // ignore null value fields, empty collection, map
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(DENA_OBJECT_ID_FIELD)) {
                refinedFields.put(DENA_OBJECT_ID_FIELD, entry.getValue());
                continue;
            }

            if (entry.getValue() == null) {
                // ignore null
                continue;
            }

            if (ClassUtils.isCollectionType(entry.getValue()) && ((Collection) entry.getValue()).size() == 0) {
                // ignore empty collection
                continue;
            }

            if (ClassUtils.isCollectionType(entry.getValue()) &&
                    !CollectionUtils.isPrimitiveCollection((Collection) entry.getValue())) {

                // ignore collection that contain non primitive, or String type
                if (!CollectionUtils.isStringCollection((Collection) entry.getValue())) {
                    continue;
                }
            }


            if (entry.getValue() instanceof Map) {
                // ignore map
                continue;
            }

            if (!ClassUtils.isPrimitiveType(entry.getValue()) && !ClassUtils.isRelationType(entry.getValue())
                    && !ClassUtils.isCollectionType(entry.getValue())) {
                // ignore user define type
                continue;
            }

            if (ClassUtils.isRelationType(entry.getValue())) {
                Relation<?> relation = (Relation<?>) entry.getValue();
                if (CollectionUtils.isNotEmpty(relation.getAllRelatedObjects())) {
                    Set<RelatedObject> relatedObjects = (Set<RelatedObject>) refinedFields.getOrDefault(RELATED_OBJECT_FIELD, new HashSet<>());
                    relatedObjects.addAll(relation.getAllRelatedObjects());
                    refinedFields.put(RELATED_OBJECT_FIELD, relatedObjects);
                }
            } else {
                refinedFields.put(entry.getKey(), entry.getValue());
            }
        }


        return refinedFields;
    }


    public static Map<String, Object> findAllFields(Object targetObject) {
        List<Field> fieldList = ReflectionUtils.findInstanceVariables(targetObject);
        Map<String, Method> getterMethodList = ReflectionUtils.findGetterMethods(targetObject);

        Map<String, Object> returnMap = new HashMap<>();

        // find result of getter method
        if (MapUtils.isNotEmpty(getterMethodList)) {
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
        if (CollectionUtils.isNotEmpty(fieldList)) {
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

    public static boolean isObjectIdSet(Map<String, Object> denaObject) {
        return denaObject.containsKey(DENA_OBJECT_ID_FIELD) && denaObject.get(DENA_OBJECT_ID_FIELD) != null;
    }

    /**
     * Inject field dena_object_id and specified value to target object.
     * if field with that name exist then only set value to field.
     *
     * @param targetObject
     * @param objectId
     * @param <T>
     * @return
     */
    public static <T> T setObjectId(final T targetObject, final String objectId) {

        try {
            if (findAllFields(targetObject).containsKey(DENA_OBJECT_ID_FIELD)) {
                ReflectionUtils.forceSetField(targetObject, DENA_OBJECT_ID_FIELD, objectId);
                return targetObject;
            } else {
                Class<T> newClass = (Class<T>) ReflectionUtils.injectPublicFieldToClass(targetObject.getClass(), String.class, DENA_OBJECT_ID_FIELD);
                T newObject = ReflectionUtils.callDefaultConstructor(newClass);
                ReflectionUtils.copyObject(targetObject, newObject);
                ReflectionUtils.forceSetField(newObject, DENA_OBJECT_ID_FIELD, objectId);

                return newObject;
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            throw new DenaFault(String.format("Can not set objectId [%s]", objectId), ex);
        }

    }

    public static Optional<String> findObjectId(Object targetObject) {
        return Optional.ofNullable(String.valueOf(findAllFields(targetObject).get(DENA_OBJECT_ID_FIELD)));
    }
}