package com.dena.client.common.web.HttpClient.dto.request;

import com.dena.client.common.utils.MapUtils;
import com.dena.client.common.web.HttpClient.dto.Parameter;

import java.util.*;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */

public class CreateObjectRequest {

    private List<Parameter> parameterList = new ArrayList<>();

    private Map<String, String> headers = new LinkedHashMap<>();

    private Map<String, Object> requestBodyMap;

    private String BaseURL;

    private String appId;

    private String typeName;

    public List<Parameter> getParameterList() {
        return Collections.unmodifiableList(parameterList);
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }


    public String getBaseURL() {
        return BaseURL;
    }

    public void setBaseURL(String baseURL) {
        this.BaseURL = baseURL;
    }

    public Map<String, Object> getRequestBodyMap() {
        return requestBodyMap;
    }

    public void setRequestBodyMap(Map<String, Object> requestBodyMap) {
        this.requestBodyMap = requestBodyMap;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        if (!appId.startsWith("/")) {
            appId = "/" + appId;
        }

        this.appId = appId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        if (!typeName.startsWith("/")) {
            typeName = "/" + typeName;
        }

        this.typeName = typeName;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    public static final class CreateObjectRequestBuilder {
        private CreateObjectRequest createObjectRequest;

        private CreateObjectRequestBuilder() {
            createObjectRequest = new CreateObjectRequest();
        }

        public static CreateObjectRequestBuilder aCreateObjectRequest() {
            return new CreateObjectRequestBuilder();
        }

        public CreateObjectRequestBuilder withParameterList(List<Parameter> parameterList) {
            createObjectRequest.setParameterList(parameterList);
            return this;
        }

        public CreateObjectRequestBuilder withParameterList(Map<String, Object> parameterMap) {
            if (MapUtils.isNotEmpty(parameterMap)) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    createObjectRequest.parameterList.add(new Parameter(entry.getKey(), entry.getValue().toString()));
                }
            }
            return this;
        }

        public CreateObjectRequestBuilder withRequestBodyMap(Map<String, Object> requestBodyMap) {
            createObjectRequest.setRequestBodyMap(requestBodyMap);
            return this;
        }

        public CreateObjectRequestBuilder withBaseURL(String baseURL) {
            createObjectRequest.setBaseURL(baseURL);
            return this;
        }

        public CreateObjectRequestBuilder withAppId(String appId) {
            createObjectRequest.setAppId(appId);
            return this;
        }


        public CreateObjectRequestBuilder withTypeName(String typeName) {
            createObjectRequest.setTypeName(typeName);
            return this;
        }

        public CreateObjectRequestBuilder withHeader(String headerName, String headerValue) {
            createObjectRequest.headers.put(headerName, headerValue);
            return this;
        }


        public CreateObjectRequest build() {
            return createObjectRequest;
        }
    }
}
