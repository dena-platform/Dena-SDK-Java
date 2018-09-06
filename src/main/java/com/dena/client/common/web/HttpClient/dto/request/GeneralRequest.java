package com.dena.client.common.web.HttpClient.dto.request;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Javad Alimohammadi<bs.alimohammadi@yahoo.com>
 */

public class GeneralRequest {
    private Map<String, Object> requestBodyMap = new LinkedHashMap<>();

    private Map<String, String> headers = new LinkedHashMap<>();

    private String baseURL;

    private String appId;

    public Map<String, Object> getRequestBodyMap() {
        return requestBodyMap;
    }

    public void setRequestBodyMap(Map<String, Object> requestBodyMap) {
        this.requestBodyMap = requestBodyMap;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static final class GeneralRequestBuilder {
        private GeneralRequest generalRequest;

        private GeneralRequestBuilder() {
            generalRequest = new GeneralRequest();
        }

        public static GeneralRequestBuilder aGeneralRequest() {
            return new GeneralRequestBuilder();
        }

        public GeneralRequestBuilder withRequestBodyMap(Map<String, Object> requestBodyMap) {
            generalRequest.setRequestBodyMap(requestBodyMap);
            return this;
        }

        public GeneralRequestBuilder withHeader(String headerName, String headerValue) {
            generalRequest.headers.put(headerName, headerValue);
            return this;
        }

        public GeneralRequestBuilder withBaseURL(String BaseURL) {
            generalRequest.setBaseURL(BaseURL);
            return this;
        }

        public GeneralRequestBuilder withAPPId(String appId) {
            generalRequest.setAppId(appId);
            return this;
        }


        public GeneralRequest build() {
            return generalRequest;
        }
    }


}
