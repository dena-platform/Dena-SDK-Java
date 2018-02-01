package com.dena.client.service;

import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public interface DenaMapper {
     String serializeObject(Object targetObject);

    Map<String, Object> findAllFields(Object targetObject);

    String findTypeName(Object targetObject);

    <T> boolean isObjectIdSet(T targetObject);

    <T> T setObjectId(T targetObject, String objectId);
}
