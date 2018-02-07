package com.dena.client.common.web.HttpClient;


import com.dena.client.common.exception.DenaFault;
import com.dena.client.common.web.HttpClient.dto.response.DenaResponse;
import com.dena.client.common.web.HttpClient.dto.response.ErrorResponse;
import com.dena.client.common.web.HttpClient.dto.Parameter;
import com.dena.client.common.utils.StringUtils;
import com.dena.client.common.utils.JSONMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class HttpClient {
    private final static Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static final int DEFAULT_CONNECTION_TIMEOUT_SECOND = 20;

    private static final int DEFAULT_READ_TIMEOUT_SECOND = 20;

    private OkHttpClient OK_HTTP_CLIENT;

    private static HttpClient httpClientInstnce;

    private HttpClient(int connectionTimeout, int readTimeout) {
        // todo: config write timeout setting
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);

        this.OK_HTTP_CLIENT = httpClient.build();

    }

    public static synchronized HttpClient getInstance(int connectionTimeout, int readTimeout) {
        if (httpClientInstnce == null) {
            httpClientInstnce = new HttpClient(connectionTimeout, readTimeout);
        }

        return httpClientInstnce;
    }

    public static synchronized HttpClient getInstance() {
        return getInstance(DEFAULT_CONNECTION_TIMEOUT_SECOND, DEFAULT_READ_TIMEOUT_SECOND);
    }

    public DenaResponse getData(final String URL, List<Parameter> parameterList) throws DenaFault {
        log.info("Fetching data from address [{}] with parameters {}", URL, parameterList);

        Request request = prepareGetClient(URL, parameterList);

        log.info("Fetching data from address [{}]", request.url());

        DenaResponse response = sendRequest(URL, parameterList, request);

        log.info("Fetching data from address [{}] successfully", request.url());
        return response;
    }

    public DenaResponse postData(final String URL, List<Parameter> parameterList, RequestBody requestBody) throws DenaFault {
        Request request = preparePostClient(URL, parameterList, requestBody);
        log.debug("Posting data to address [{}]", request.url());

        DenaResponse denaResponse = sendRequest(URL, parameterList, request);

        return denaResponse;
    }

    public DenaResponse putData(final String URL, List<Parameter> parameterList, RequestBody requestBody) throws DenaFault {
        Request request = preparePutClient(URL, parameterList, requestBody);
        log.debug("Putting data to address [{}]", request.url());

        DenaResponse denaResponse = sendRequest(URL, parameterList, request);

        return denaResponse;
    }


    private Request preparePostClient(final String URL, List<Parameter> parameterList, RequestBody requestBody) {
        HttpUrl httpUrl = HttpUrl.parse(URL);

        HttpUrl result = addParameterToHttpURL(httpUrl, parameterList);
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

    private Request preparePutClient(final String URL, List<Parameter> parameterList, RequestBody requestBody) {
        HttpUrl httpUrl = HttpUrl.parse(URL);

        HttpUrl result = addParameterToHttpURL(httpUrl, parameterList);
        Request request = new Request.Builder()
                .url(result)
                .put(requestBody)
                .build();
        return request;
    }


    private HttpUrl addParameterToHttpURL(final HttpUrl httpUrl, List<Parameter> parameterList) {
        HttpUrl result = httpUrl;

        for (Parameter parameter : parameterList) {
            String parameterName = parameter.getName();
            String parameterValue = parameter.getValue();
            if (StringUtils.isBlank(parameterName))
                log.warn("Parameter name for entry [{}] is empty", parameter);

            if (StringUtils.isBlank(parameterValue))
                log.warn("Parameter value for entry [{}] is empty", parameter);

            if (StringUtils.isNoneBlank(parameterName, parameterValue)) {
                result = result.newBuilder().addQueryParameter(parameterName, parameterValue).build();
            }
        }

        return result;
    }

    private DenaResponse sendRequest(final String URL, List<Parameter> parameters, Request request) throws DenaFault {
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                String responseBody = response.body().string();
                String message = String.format("Exception when connected to address [%s] " +
                        "with parameters %s response code [%s] response body [%s]", URL, parameters, response.code(), responseBody);

                ErrorResponse errorResponse = JSONMapper.createObjectFromJSON(responseBody, ErrorResponse.class);
                throw new DenaFault(message, errorResponse);
            } else {
                String responseBody = response.body().string();
                return JSONMapper.createObjectFromJSON(responseBody, DenaResponse.class);
            }
        } catch (IOException ex) {
            String message = String.format("Exception when connecting to address [%s] with parameters %s", URL, parameters);
            throw new DenaFault(message, ex);
        }

    }
}


