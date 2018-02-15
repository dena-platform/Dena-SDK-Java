package com.dena.client;

import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.exception.ErrorCode;
import com.dena.client.core.feature.persistence.DenaSerializer;
import com.dena.client.common.web.HttpClient.HttpClientManager;
import com.dena.client.common.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.common.web.HttpClient.dto.response.DenaObjectResponse;
import com.dena.client.common.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.common.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

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


    public static <T> T saveOrUpdate(final T denaObject) throws DenaFault {
        if (denaObject == null) {
            throw DenaFault.makeException(ErrorCode.NULL_ENTITY, new IllegalAccessException());
        }

        final Map<String, Object> serializedObject = DenaSerializer.serializeToMap(denaObject);
        final String typeName = ClassUtils.findTypeName(denaObject);

        CreateObjectRequest createObjectRequest = aCreateObjectRequest()
                .withRequestBodyMap(serializedObject)
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .build();

        // object id have not set before so send create new object request
        if (!DenaSerializer.isObjectIdSet(serializedObject)) {
            DenaResponse denaResponse = HttpClientManager.createNewDenaObject(createObjectRequest);
            DenaObjectResponse denaObjectResponse = denaResponse.getDenaObjectResponseList().get(0);
            DenaSerializer.setObjectId(denaObject, denaObjectResponse.getObjectId());
            DenaSerializer.setCreatedTime(denaObject, denaResponse.getTimestamp());
            DenaSerializer.setCount(denaObject, denaResponse.getCount());
            log.debug("Object [{}] is created successfully with id [{}].", denaObject, denaObjectResponse.getObjectId());
            return denaObject;
        } else {
            // send update object request
            DenaResponse denaResponse = HttpClientManager.updateDenaObject(createObjectRequest);
            DenaSerializer.setCreatedTime(denaObject, denaResponse.getTimestamp());
            DenaSerializer.setCount(denaObject, denaResponse.getCount());
            log.debug("Object [{}] is updated successfully.", denaObject);
            return denaObject;
        }

    }

    public static <T> Set<T> saveOrUpdate(final Set<T> denaObjects) {
        return null;
    }


}
