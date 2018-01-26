package com.dena.client;

import com.dena.client.service.DenaMapperImpl;
import com.dena.client.service.web.HttpClient.HttpClientManager;
import com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.service.web.HttpClient.dto.response.DenaObjectResponse;
import com.dena.client.service.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.utils.DenaReflectionUtils;
import com.dena.client.utils.JSONMapper;

import java.util.List;
import java.util.Map;

import static com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest.CreateObjectRequestBuilder.aCreateObjectRequest;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class Dena {
    private static String DENA_URL = "http://localhost:8090";

    private final static DenaMapperImpl DENA_MAPPER = new DenaMapperImpl();

    private static String APP_ID;

    public static void initApp(String AppId) {
        APP_ID = AppId;
    }

    public static void setEndPoint(String endPoint) {
        DENA_URL = endPoint;
    }


    public static <T> T saveOrUpdate(T object) {
        Map<String, Object> fields = DENA_MAPPER.findAllFields(object);
        String typeName = DENA_MAPPER.findTypeName(object);
        String requestDataBody = JSONMapper.createJSONFromObject(fields);

        CreateObjectRequest createObjectRequest = aCreateObjectRequest()
                .withRequestBodyContent(requestDataBody)
                .withBaseURL(DENA_URL)
                .withAppId(APP_ID)
                .withTypeName(typeName)
                .build();

        DenaResponse denaResponse = HttpClientManager.postData(createObjectRequest);
        List<DenaObjectResponse> denaObjectResponseList = denaResponse.getDenaObjectResponseList();

        return null;
    }
}
