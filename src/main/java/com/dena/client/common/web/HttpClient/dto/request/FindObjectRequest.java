package com.dena.client.common.web.HttpClient.dto.request;

import com.dena.client.common.web.HttpClient.dto.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class FindObjectRequest {

    private List<Parameter> parameterList = new ArrayList<>();

    private String BaseURL;

    private String appId;

    private String objectId;

    private String typeName;

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }


    public String getBaseURL() {
        return BaseURL;
    }

    public void setBaseURL(String baseURL) {
        BaseURL = baseURL;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        if (!objectId.startsWith("/")) {
            objectId = "/" + objectId;
        }

        this.objectId = objectId;
    }

    public static final class FindObjectRequestBuilder {
        private FindObjectRequest findObjectRequest;

        private FindObjectRequestBuilder() {
            findObjectRequest = new FindObjectRequest();
        }

        public static FindObjectRequestBuilder aFindObjectRequest() {
            return new FindObjectRequestBuilder();
        }

        public FindObjectRequestBuilder withParameterList(List<Parameter> parameterList) {
            findObjectRequest.setParameterList(parameterList);
            return this;
        }

        public FindObjectRequestBuilder withBaseURL(String BaseURL) {
            findObjectRequest.setBaseURL(BaseURL);
            return this;
        }

        public FindObjectRequestBuilder withObjectId(String objectId) {
            findObjectRequest.setObjectId(objectId);
            return this;
        }

        public FindObjectRequestBuilder withAppId(String appId) {
            findObjectRequest.setAppId(appId);
            return this;
        }

        public FindObjectRequestBuilder withTypeName(String typeName) {
            findObjectRequest.setTypeName(typeName);
            return this;
        }

        public FindObjectRequest build() {
            return findObjectRequest;
        }
    }
}
