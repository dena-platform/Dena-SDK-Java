package com.dena.client;

import com.dena.client.exception.DenaFault;
import com.dena.client.exception.ErrorCode;
import com.dena.client.service.DenaSerializer;
import com.dena.client.service.web.HttpClient.HttpClientManager;
import com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.service.web.HttpClient.dto.response.DenaObjectResponse;
import com.dena.client.service.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.utils.DenaClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest.CreateObjectRequestBuilder.aCreateObjectRequest;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class Dena {
    public static final String DENA_OBJECT_ID_FIELD = "object_id";

    private final static Logger log = LoggerFactory.getLogger(Dena.class);

    private static String DENA_URL = "http://localhost:8090/v1";

    private final static DenaSerializer DENA_SERIALIZER = new DenaSerializer();

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

        final Map<String, Object> serializedObject = DENA_SERIALIZER.serializeToMap(denaObject);
        final String typeName = DenaClassUtils.findTypeName(denaObject);

        CreateObjectRequest createObjectRequest = aCreateObjectRequest()
                .withRequestBodyMap(serializedObject)
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .build();

        // object id have not set before, send create new object request
        if (!DENA_SERIALIZER.isObjectIdSet(serializedObject)) {
            DenaResponse denaResponse = HttpClientManager.createNewDenaObject(createObjectRequest);
            DenaObjectResponse denaObjectResponse = denaResponse.getDenaObjectResponseList().get(0);
            DENA_SERIALIZER.setObjectId(denaObject, denaObjectResponse.getObjectId());
            log.debug("Object [{}] is created successfully with id [{}].", denaObject, denaObjectResponse.getObjectId());
            return denaObject;
        } else {
            // send update object request
            HttpClientManager.updateDenaObject(createObjectRequest);
            log.debug("Object [{}] is updated successfully.", denaObject);
            return denaObject;
        }

    }


}
