package com.dena.client.service;

import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public interface DenaMapper {
    Map<String, Object> findAllFields(Object object);

    String findTypeName(Object object);

    <T> T setObjectId(T object, String objectId);
}
