package com.dena.client.service.web.HttpClient.httpClient;


import ir.peykasa.common.exception.ServiceException;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ir.peykasa.common.config.DorsaSystemProperty.resolveIntSystemProperty;


/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */


public final class HttpClient {
    private final static Logger log = LoggerFactory.getLogger(HttpClient.class);
    private static  OkHttpClient HTTP_CLIENT ;



    public void initHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(resolveIntSystemProperty("httpClient.client.connection_timeout"), TimeUnit.SECONDS)
                .readTimeout(resolveIntSystemProperty("httpClient.client.read_timeout"), TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS);


        HTTP_CLIENT = httpClient.build();

    }

    public static Response fetchData(final String URL, Map<String, String> parameters) throws ServiceException {
        log.info("Fetching data from address [{}] with parameters[{}]", URL, parameters);

        Request request = prepareGetClient(URL, parameters);
        log.info("Fetching data from address [{}]", request.url());
        Response response = sendRequest(URL, parameters, request);
        log.info("Fetching data from address [{}] successfully", request.url());
        return response;
    }

    public static Response postData(final String URL, Map<String, String> parameters, RequestBody requestBody) throws ServiceException {
        Request request = preparePostClient(URL, parameters, requestBody);
        log.info("Posting data to address [{}]", request.url());
        Response response = sendRequest(URL, parameters, request);

        String body;
        try {
            body = response.peekBody(100000).string();
        } catch (IOException e) {
            body = "";
            log.error(e.getMessage(),e);
        }

        log.info("Posting data to address [{}] successfully. response code: [{}] with body: [{}]",
                request.url(), response.code(), body);

        return response;
    }

    private static Request preparePostClient(final String URL, Map<String, String> parameters, RequestBody requestBody) {
        HttpUrl httpUrl = HttpUrl.parse(URL);


        HttpUrl result = addParameterToHttpURL(httpUrl, parameters);
        Request request = new Request.Builder()
                .url(result)
                .post(requestBody)
                .build();
        return request;

    }

    private static Request prepareGetClient(final String URL, Map<String, String> parameters) {
        HttpUrl httpUrl = HttpUrl.parse(URL);
        HttpUrl result = addParameterToHttpURL(httpUrl, parameters);
        Request request = new Request.Builder()
                .url(result)
                .build();

        return request;
    }

    private static HttpUrl addParameterToHttpURL(final HttpUrl httpUrl, Map<String, String> parameters) {
        HttpUrl result = httpUrl;

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String parameterName = entry.getKey();
            String parameterValue = entry.getValue();
            if (StringUtils.isBlank(parameterName))
                log.warn("Parameter name for entry [{}] is empty", entry.toString());

            if (StringUtils.isBlank(parameterValue))
                log.warn("Parameter value for entry [{}] is empty", entry);

            if (StringUtils.isNoneBlank(parameterName, parameterValue)) {
                result = result.newBuilder().addQueryParameter(parameterName, parameterValue).build();
            }
        }

        return result;
    }

    private static Response sendRequest(final String URL, Map<String, String> parameters, Request request) throws ServiceException {
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                String message = String.format("Exception when connected to address [%s] " +
                        "with parameters[%s] response code [%s] response body[%s]", URL, parameters, response.code(), response.body().string());
                throw new ServiceException(message);
            }

            return response;
        } catch (IOException e) {
            throw new ServiceException("Exception in connecting to address [" + URL + "] with parameters[" + parameters + "]", e);
        }

    }
}
