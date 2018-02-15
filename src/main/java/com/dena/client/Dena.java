package com.dena.client;

import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.exception.ErrorCode;
import com.dena.client.common.web.HttpClient.dto.request.DeleteObjectRequest;
import com.dena.client.core.feature.persistence.DenaSerializer;
import com.dena.client.common.web.DenaClientManager;
import com.dena.client.common.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.common.web.HttpClient.dto.response.DenaObjectResponse;
import com.dena.client.common.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.common.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

import static com.dena.client.common.web.HttpClient.dto.request.CreateObjectRequest.CreateObjectRequestBuilder.aCreateObjectRequest;

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
            throw DenaFault.makeException(ErrorCode.NULL_ENTITY, new IllegalAccessException());
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

    public static long remove(Object denaObject) throws DenaFault {
        if (denaObject == null) {
            throw DenaFault.makeException(ErrorCode.NULL_ENTITY, new IllegalAccessException());
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
}
