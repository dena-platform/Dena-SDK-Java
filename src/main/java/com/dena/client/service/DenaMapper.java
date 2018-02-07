package com.dena.client.service;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public interface DenaMapper {

    String findTypeName(Object targetObject);

    <T> boolean isObjectIdSet(T targetObject);

    <T> T setObjectId(T targetObject, String objectId);
}
