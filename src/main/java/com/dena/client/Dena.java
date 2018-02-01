package com.dena.client;

import com.dena.client.exception.DenaFault;
import com.dena.client.service.DenaMapper;
import com.dena.client.service.DenaMapperImpl;
import com.dena.client.service.web.HttpClient.HttpClientManager;
import com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.service.web.HttpClient.dto.response.DenaObjectResponse;
import com.dena.client.service.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.utils.DenaReflectionUtils;
import com.dena.client.utils.JSONMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest.CreateObjectRequestBuilder.aCreateObjectRequest;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class Dena {
    private final static Logger log = LoggerFactory.getLogger(Dena.class);

    private static String DENA_URL = "http://localhost:8090/v1";

    private final static DenaMapper DENA_MAPPER = new DenaMapperImpl();

    private static String APP_ID;

    public static void initApp(String AppId) {
        APP_ID = AppId;
    }

    public static void setEndPoint(String endPoint) {
        DENA_URL = endPoint;
    }


    public static <T> T saveOrUpdate(T denaObject) throws DenaFault {
        final String requestBody = DENA_MAPPER.serializeObject(denaObject);
        final String typeName = DENA_MAPPER.findTypeName(denaObject);

        CreateObjectRequest createObjectRequest = aCreateObjectRequest()
                .withRequestBodyContent(requestBody)
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .build();

        // object id have not set before, send create object request
        if (!DENA_MAPPER.isObjectIdSet(denaObject)) {
            DenaResponse denaResponse = HttpClientManager.postData(createObjectRequest);
            DenaObjectResponse denaObjectResponse = denaResponse.getDenaObjectResponseList().get(0);
            denaObject = DENA_MAPPER.setObjectId(denaObject, denaObjectResponse.getObjectId());
            log.debug("Object [{}] is created successfully with id [{}].", denaObject, denaObjectResponse.getObjectId());
            return denaObject;
        } else {
            // send update object request
            HttpClientManager.putData(createObjectRequest);
            log.debug("Object [{}] is updated successfully.", denaObject);
            return denaObject;
        }

    }


}
