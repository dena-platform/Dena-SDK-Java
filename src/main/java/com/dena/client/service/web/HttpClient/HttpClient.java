package com.dena.client.service.web.HttpClient;


import com.dena.client.exception.DenaFault;
import com.dena.client.service.web.ErrorResponse;
import com.dena.client.service.web.HttpClient.dto.Parameter;
import com.dena.client.utils.DenaStringUtils;
import com.dena.client.utils.JSONMapper;
import ir.peykasa.common.exception.ServiceException;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ir.peykasa.common.config.DorsaSystemProperty.resolveIntSystemProperty;


/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */


public final class HttpClient {
    private final static Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static final int DEFAULT_CONNECTION_TIMEOUT_SECOND = 20;

    private static final int DEFAULT_READ_TIMEOUT_SECOND = 20;


    private OkHttpClient HTTP_CLIENT;

    private static HttpClient httpClient;

    private HttpClient(int connectionTimeout, int readTimeout) {
        // todo: config write timeout setting
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);

        this.HTTP_CLIENT = httpClient.build();

    }

    public static synchronized HttpClient getInstance(int connectionTimeout, int readTimeout) {
        if (httpClient == null) {
            httpClient = new HttpClient(connectionTimeout, readTimeout);
        }

        return httpClient;
    }

    public static synchronized HttpClient getInstance() {
        return getInstance(DEFAULT_CONNECTION_TIMEOUT_SECOND, DEFAULT_READ_TIMEOUT_SECOND);
    }

    public Response getData(final String URL, List<Parameter> parameterList) throws ServiceException {
        log.info("Fetching data from address [{}] with parameters[{}]", URL, parameterList);

        Request request = prepareGetClient(URL, parameterList);

        log.info("Fetching data from address [{}]", request.url());

        Response response = sendRequest(URL, parameterList, request);

        log.info("Fetching data from address [{}] successfully", request.url());
        return response;
    }

    public Response postData(final String URL, Map<String, String> parameters, RequestBody requestBody) throws ServiceException {
        Request request = preparePostClient(URL, parameters, requestBody);
        log.info("Posting data to address [{}]", request.url());
        Response response = sendRequest(URL, parameters, request);

        String body;
        try {
            body = response.peekBody(100000).string();
        } catch (IOException e) {
            body = "";
            log.error(e.getMessage(), e);
        }

        log.info("Posting data to address [{}] successfully. response code: [{}] with body: [{}]",
                request.url(), response.code(), body);

        return response;
    }

    private Request preparePostClient(final String URL, Map<String, String> parameters, RequestBody requestBody) {
        HttpUrl httpUrl = HttpUrl.parse(URL);

        HttpUrl result = addParameterToHttpURL(httpUrl, parameters);
        Request request = new Request.Builder()
                .url(result)
                .post(requestBody)
                .build();
        return request;

    }

    private Request prepareGetClient(final String URL, List<Parameter> parameterList) {
        HttpUrl httpUrl = HttpUrl.parse(URL);
        HttpUrl result = addParameterToHttpURL(httpUrl, parameterList);
        Request request = new Request.Builder()
                .url(result)
                .build();

        return request;
    }

    private HttpUrl addParameterToHttpURL(final HttpUrl httpUrl, List<Parameter> parameterList) {
        HttpUrl result = httpUrl;

        for (Parameter parameter : parameterList) {
            String parameterName = parameter.getName();
            String parameterValue = parameter.getValue();
            if (DenaStringUtils.isBlank(parameterName))
                log.warn("Parameter name for entry [{}] is empty", parameter);

            if (DenaStringUtils.isBlank(parameterValue))
                log.warn("Parameter value for entry [{}] is empty", parameter);

            if (DenaStringUtils.isNoneBlank(parameterName, parameterValue)) {
                result = result.newBuilder().addQueryParameter(parameterName, parameterValue).build();
            }
        }

        return result;
    }

    private Response sendRequest(final String URL, Map<String, String> parameters, Request request) throws DenaFault {
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                String responseBody = response.body().string();
                String message = String.format("Exception when connected to address [%s] " +
                        "with parameters[%s] response code [%s] response body[%s]", URL, parameters, response.code(), responseBody);

                ErrorResponse errorResponse = JSONMapper.createObjectFromJSON(responseBody, ErrorResponse.class);

                throw new DenaFault(message);
            }

            return response;
        } catch (IOException ex) {
            throw new DenaFault("Exception when connecting to address [" + URL + "] with parameters[" + parameters + "]", ex);
        }

    }
}
