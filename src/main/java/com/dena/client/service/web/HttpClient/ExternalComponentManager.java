package com.dena.client.service.web.HttpClient;

import com.dena.client.exception.DenaFault;
import com.dena.client.service.web.HttpClient.dto.ExternalComponentFetchRequest;
import com.dena.client.service.web.HttpClient.dto.ExternalComponentPostDataRequest;
import com.dena.client.service.web.HttpClient.httpClient.HttpClient;
import com.dena.client.utils.JSONMapper;
import ir.peykasa.common.dto.Property;
import ir.peykasa.common.exception.ServiceException;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Author: Javad Alimohammadi Email:bc_alimohammadi@yahoo.com
 * DateTime: 4/7/13 11:23 AM
 */

public class ExternalComponentManager {
    private final static Logger log = getLogger(ExternalComponentManager.class);


    public static <T> T fetchData(ExternalComponentFetchRequest fetchRequest) throws DenaFault {
        String URL = fetchRequest.getURL();
        Map<String, String> parametersKeyValue = getParameters(fetchRequest.getProperties());

        Response response = HttpClient.fetchData(URL, parametersKeyValue);

        try {
            String bodyResponse = response.body().string();
            if (fetchRequest.getReturnType() == null) {
                return JSONMapper.createObjectFromJSON(bodyResponse, fetchRequest.getTypeReference());
            } else {
                return JSONMapper.createObjectFromJSON(bodyResponse, fetchRequest.getReturnType());
            }
        } catch (final Exception ex) {
            throw new DenaFault("Exception in getting data from component [" + fetchRequest.getComponentName() + "]", ex);
        }

    }

    public static <T> T postData(ExternalComponentPostDataRequest fetchRequest) throws DenaFault {
        String URL = fetchRequest.getURL();
        Map<String, String> parametersKeyValue = getParameters(fetchRequest.getProperties());
        RequestBody requestBody = RequestBody.create(fetchRequest.getMediaType(), fetchRequest.getRequestBodyContent());
        Response response = HttpClient.postData(URL, parametersKeyValue, requestBody);

        try {
            String bodyResponse = response.body().string();
            return JSONMapper.createObjectFromJSON(bodyResponse, fetchRequest.getReturnType());
        } catch (final Exception ex) {
            throw new ServiceException("Exception in posting data to component [" + fetchRequest.getComponentName() + "]", ex);
        }

    }

    private static Map<String, String> getParameters(List<Property> properties) {
        Map<String, String> parametersNameValue = new LinkedHashMap<>();
        for (Property property : properties) {
            parametersNameValue.put(property.getName(), property.getValue());
        }

        return parametersNameValue;

    }
}
