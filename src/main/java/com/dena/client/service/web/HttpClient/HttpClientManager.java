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
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class HttpClientManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final static Logger log = getLogger(HttpClientManager.class);

    private static final HttpClient DENA_HTTP_CLIENT = HttpClient.getInstance();

    public static DenaResponse getData(GetRequest getRequest) throws DenaFault {
        String URL = getRequest.getURL();
        return DENA_HTTP_CLIENT.getData(URL, getRequest.getParameterList());

    }

    public static DenaResponse postData(CreateObjectRequest fetchRequest) throws DenaFault {
        String fullURL = fetchRequest.getBaseURL() + fetchRequest.getAppId() + fetchRequest.getTypeName();
        RequestBody requestBody = RequestBody.create(JSON, fetchRequest.getRequestBodyContent());
        DenaResponse denaResponse = DENA_HTTP_CLIENT.postData(fullURL, fetchRequest.getParameterList(), requestBody);
        log.debug("Successfully posted data to address [{}], parameters {}, body [{}]", fullURL, fetchRequest.getParameterList(), fetchRequest.getRequestBodyContent());

        return denaResponse;
    }


}
