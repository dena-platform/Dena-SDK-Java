package com.dena.client.service.web.HttpClient;

import com.dena.client.exception.DenaFault;
import com.dena.client.service.web.HttpClient.dto.request.GetRequest;
import com.dena.client.service.web.HttpClient.dto.request.PostRequest;
import com.dena.client.utils.JSONMapper;
import ir.peykasa.common.exception.ServiceException;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Author: Javad Alimohammadi Email:bc_alimohammadi@yahoo.com
 * DateTime: 4/7/13 11:23 AM
 */

public class HttpClientManager {
    private final static Logger log = getLogger(HttpClientManager.class);

    private static final DenaHttpClient DENA_HTTP_CLIENT = DenaHttpClient.getInstance();

    public static <T> T getData(GetRequest getRequest) throws DenaFault {
        String URL = getRequest.getURL();

        Response response = DENA_HTTP_CLIENT.getData(URL, getRequest.getParameterList());

        String bodyResponse = response.body().string();
        if (getRequest.getReturnType() == null) {
            return JSONMapper.createObjectFromJSON(bodyResponse, getRequest.getTypeReference());
        } else {
            return JSONMapper.createObjectFromJSON(bodyResponse, getRequest.getReturnType());
        }

    }

    public static <T> T postData(PostRequest fetchRequest) throws DenaFault {
        String URL = fetchRequest.getURL();
        Map<String, String> parametersKeyValue = getParameters(fetchRequest.getParameterList());
        RequestBody requestBody = RequestBody.create(fetchRequest.getMediaType(), fetchRequest.getRequestBodyContent());
        Response response = DenaHttpClient.postData(URL, parametersKeyValue, requestBody);

        try {
            String bodyResponse = response.body().string();
            return JSONMapper.createObjectFromJSON(bodyResponse, fetchRequest.getReturnType());
        } catch (final Exception ex) {
            throw new ServiceException("Exception in posting data to component [" + fetchRequest.getComponentName() + "]", ex);
        }

    }


}
