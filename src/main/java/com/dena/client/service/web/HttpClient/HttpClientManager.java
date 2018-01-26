package com.dena.client.service.web.HttpClient;

import com.dena.client.exception.DenaFault;
import com.dena.client.service.web.HttpClient.dto.request.GetRequest;
import com.dena.client.service.web.HttpClient.dto.request.CreateObjectRequest;
import com.dena.client.service.web.HttpClient.dto.response.DenaResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Author: Javad Alimohammadi Email:bc_alimohammadi@yahoo.com
 * DateTime: 4/7/13 11:23 AM
 */

public class HttpClientManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final static Logger log = getLogger(HttpClientManager.class);

    private static final DenaHttpClient DENA_HTTP_CLIENT = DenaHttpClient.getInstance();

    public static DenaResponse getData(GetRequest getRequest) throws DenaFault {
        String URL = getRequest.getURL();
        return DENA_HTTP_CLIENT.getData(URL, getRequest.getParameterList());

    }

    public static DenaResponse postData(CreateObjectRequest fetchRequest) throws DenaFault {
        String fullURL = fetchRequest.getBaseURL() + fetchRequest.getAppId();
        RequestBody requestBody = RequestBody.create(JSON, fetchRequest.getRequestBodyContent());
        return DENA_HTTP_CLIENT.postData(fullURL, fetchRequest.getParameterList(), requestBody);
    }


}
