package com.dena.client;

import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.exception.ErrorCode;
import com.dena.client.common.utils.CollectionUtils;
import com.dena.client.common.utils.ReflectionUtils;
import com.dena.client.common.utils.StringUtils;
import com.dena.client.common.web.HttpClient.dto.request.DeleteObjectRequest;
import com.dena.client.common.web.HttpClient.dto.request.DeleteRelationRequest;
import com.dena.client.common.web.HttpClient.dto.request.FindObjectRequest;
import com.dena.client.core.feature.persistence.DenaSerializer;
import com.dena.client.common.web.DenaClientManager;
import com.dena.client.common.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.common.web.HttpClient.dto.response.DenaObjectResponse;
import com.dena.client.common.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.common.utils.ClassUtils;
import com.dena.client.core.feature.persistence.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.dena.client.common.web.HttpClient.dto.request.CreateObjectRequest.CreateObjectRequestBuilder.aCreateObjectRequest;
import static com.dena.client.common.web.HttpClient.dto.request.DeleteRelationRequest.DeleteRelationRequestBuilder.aDeleteRelationRequest;
import static com.dena.client.common.web.HttpClient.dto.request.FindObjectRequest.FindObjectRequestBuilder.aFindObjectRequest;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class Dena {

    private final static Logger log = LoggerFactory.getLogger(Dena.class);

    private static String DENA_URL = "http://localhost:8090/v1";

    private static String APP_ID;

    public static void initApp(String AppId) {
        APP_ID = AppId;
    }

    public static void setEndPoint(String endPoint) {
        DENA_URL = endPoint;
    }


    public static <T> T saveOrUpdate(T denaObject) throws DenaFault {
        if (denaObject == null) {
            throw DenaFault.makeException(ErrorCode.OBJECT_NOT_PRESENT, new IllegalAccessException());
        }

        final Map<String, Object> serializedObject = DenaSerializer.serializeToMap(denaObject);
        final String typeName = ClassUtils.findSimpleTypeName(denaObject);

        CreateObjectRequest createObjectRequest = aCreateObjectRequest()
                .withRequestBodyMap(serializedObject)
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .build();

        // object id have not set before so send create new object request
        if (!DenaSerializer.isObjectIdSet(serializedObject)) {
            DenaResponse denaResponse = DenaClientManager.createNewDenaObject(createObjectRequest);
            DenaObjectResponse denaObjectResponse = denaResponse.getDenaObjectResponseList().get(0);
            denaObject = DenaSerializer.setObjectId(denaObject, denaObjectResponse.getObjectId());
            denaObject = DenaSerializer.setCreatedTime(denaObject, denaResponse.getTimestamp());
            denaObject = DenaSerializer.setCount(denaObject, denaResponse.getCount());
            log.debug("Object [{}] is created successfully with id [{}].", denaObject, denaObjectResponse.getObjectId());
            return denaObject;
        } else {
            // send update object request
            DenaResponse denaResponse = DenaClientManager.updateDenaObject(createObjectRequest);
            denaObject = DenaSerializer.setUpdateTime(denaObject, denaResponse.getTimestamp());
            denaObject = DenaSerializer.setCount(denaObject, denaResponse.getCount());
            log.debug("Object [{}] is updated successfully.", denaObject);
            return denaObject;
        }

    }

    public static long removeObject(Object denaObject) throws DenaFault {
        if (denaObject == null) {
            throw DenaFault.makeException(ErrorCode.OBJECT_NOT_PRESENT, new IllegalAccessException());
        }

        final Map<String, Object> serializedObject = DenaSerializer.serializeToMap(denaObject);

        if (!DenaSerializer.isObjectIdSet(serializedObject)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_ID_NOT_SET, new IllegalArgumentException());
        }

        final String typeName = ClassUtils.findSimpleTypeName(denaObject);

        DeleteObjectRequest createObjectRequest = DeleteObjectRequest.DeleteObjectRequestBuilder.aDeleteObjectRequest()
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .withObjectId(DenaSerializer.findObjectId(denaObject).get())
                .build();

        DenaResponse denaResponse = DenaClientManager.deleteDenaObject(createObjectRequest);
        return denaResponse.getCount();
    }

    public static long removeObjects(Collection<?> denaObjects) throws DenaFault {
        if (CollectionUtils.isEmpty(denaObjects)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_NOT_PRESENT, new IllegalAccessException());
        }

        if (!ClassUtils.isCollectionOfSameType(denaObjects)) {
            throw DenaFault.makeException(ErrorCode.OBJECTS_IS_NOT_SAME_TYPE, new IllegalAccessException());
        }


        final Set<Map<String, Object>> serializedObjects = DenaSerializer.serializeToMap(denaObjects);

        if (!DenaSerializer.isObjectIdSet(serializedObjects)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_ID_NOT_SET, new IllegalArgumentException());
        }

        final String typeName = ClassUtils.findSimpleTypeName(denaObjects.iterator().next());

        DeleteObjectRequest createObjectRequest = DeleteObjectRequest.DeleteObjectRequestBuilder.aDeleteObjectRequest()
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .withObjectId(DenaSerializer.findObjectId(denaObjects).get())
                .build();

        DenaResponse denaResponse = DenaClientManager.deleteDenaObject(createObjectRequest);
        return denaResponse.getCount();
    }

    public static long removeRelation(Object denaObject, Relation<?> relation) throws DenaFault {
        if (denaObject == null) {
            throw DenaFault.makeException(ErrorCode.OBJECT_NOT_PRESENT, new IllegalAccessException());
        }

        if (relation == null) {
            throw DenaFault.makeException(ErrorCode.RELATION_NOT_PRESENT, new IllegalAccessException());
        }

        if (CollectionUtils.isEmpty(relation.getAllRelatedObjects())) {
            log.debug("Relation have no element");
            return 0;
        }

        final Map<String, Object> serializedObject = DenaSerializer.serializeToMap(denaObject);

        if (!DenaSerializer.isObjectIdSet(serializedObject)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_ID_NOT_SET, new IllegalArgumentException());
        }

        final String parentTypeName = ClassUtils.findSimpleTypeName(denaObject);

        DeleteRelationRequest deleteRelationRequest = aDeleteRelationRequest()
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withParentTypeName(parentTypeName)
                .withParentObjectId(DenaSerializer.findObjectId(denaObject).get())
                .withChildTypeName(relation.getRelationObjectType().get())
                .build();

        DenaResponse denaResponse = DenaClientManager.deleteRelation(deleteRelationRequest);
        return denaResponse.getCount();

    }

    public static long removeRelation(Object parentObject, Relation<?> relation, Object childObject) throws DenaFault {
        if (parentObject == null || childObject == null) {
            throw DenaFault.makeException(ErrorCode.OBJECT_NOT_PRESENT, new IllegalAccessException());
        }

        if (relation == null) {
            throw DenaFault.makeException(ErrorCode.RELATION_NOT_PRESENT, new IllegalAccessException());
        }

        if (CollectionUtils.isEmpty(relation.getAllRelatedObjects())) {
            log.debug("Relation have no element");
            return 0;
        }

        final Map<String, Object> parentSerializedObject = DenaSerializer.serializeToMap(parentObject);

        if (!DenaSerializer.isObjectIdSet(parentSerializedObject)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_ID_NOT_SET, new IllegalArgumentException());
        }

        final Map<String, Object> childSerializedObject = DenaSerializer.serializeToMap(childObject);

        if (!DenaSerializer.isObjectIdSet(childSerializedObject)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_ID_NOT_SET, new IllegalArgumentException());
        }

        final String parentTypeName = ClassUtils.findSimpleTypeName(parentObject);
        final String parentObjectId = DenaSerializer.findObjectId(parentObject).get();

        final String childTypeName = ClassUtils.findSimpleTypeName(childObject);
        final String childObjectId = DenaSerializer.findObjectId(childObject).get();


        DeleteRelationRequest deleteRelationRequest = aDeleteRelationRequest()
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withParentTypeName(parentTypeName)
                .withParentObjectId(parentObjectId)
                .withChildTypeName(childTypeName)
                .withChildObjectId(childObjectId)
                .build();

        DenaResponse denaResponse = DenaClientManager.deleteRelation(deleteRelationRequest);
        return denaResponse.getCount();


    }

    public static <T> T findById(Class<T> klass, String objectId) {
        if (klass == null) {
            throw DenaFault.makeException(ErrorCode.OBJECT_NOT_PRESENT, new IllegalAccessException());
        }

        if (StringUtils.isBlank(objectId)) {
            throw DenaFault.makeException(ErrorCode.OBJECT_ID_NOT_SET, new IllegalAccessException());
        }

        FindObjectRequest findObjectRequest = aFindObjectRequest()
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withObjectId(objectId)
                .build();

        DenaResponse denaResponse = DenaClientManager.findDenaObject(findObjectRequest);
        List<DenaObjectResponse> denaObjectResponses = denaResponse.getDenaObjectResponseList();

        if (CollectionUtils.isNotEmpty(denaObjectResponses)) {
            DenaObjectResponse denaObjectResponse = denaObjectResponses.get(0);
            return DenaSerializer.deserializeObjectResponse(klass, denaObjectResponse);
        } else {
            return null;
        }
    }

}
