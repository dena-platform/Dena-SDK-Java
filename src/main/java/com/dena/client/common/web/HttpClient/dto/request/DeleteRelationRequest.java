package com.dena.client.common.web.HttpClient.dto.request;

import com.dena.client.common.utils.MapUtils;
import com.dena.client.common.utils.StringUtils;
import com.dena.client.common.web.HttpClient.dto.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class DeleteRelationRequest {
    private List<Parameter> parameterList = new ArrayList<>();

    private String BaseURL;

    private String appId;

    private String parentTypeName;

    private String parentObjectId;

    private String childTypeName;

    private String childObjectId;

    public String getBaseURL() {
        return BaseURL;
    }

    public void setBaseURL(String baseURL) {
        this.BaseURL = baseURL;
    }

    public List<Parameter> getParameterList() {
        return Collections.unmodifiableList(parameterList);
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
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

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        if (!parentTypeName.startsWith("/")) {
            parentTypeName = "/" + parentTypeName;
        }

        this.parentTypeName = parentTypeName;
    }

    public void setChildTypeName(String childTypeName) {
        if (!childTypeName.startsWith("/")) {
            childTypeName = "/" + childTypeName;
        }

        this.childTypeName = childTypeName;
    }

    public String getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(String parentObjectId) {
        if (!parentObjectId.startsWith("/")) {
            parentObjectId = "/" + parentObjectId;
        }

        this.parentObjectId = parentObjectId;
    }

    public String getChildObjectId() {
        return childObjectId;
    }

    public void setChildObjectId(String childObjectId) {
        if (!childObjectId.startsWith("/")) {
            childObjectId = "/" + childObjectId;
        }

        this.childObjectId = childObjectId;
    }


    public static final class DeleteRelationRequestBuilder {
        private DeleteRelationRequest deleteRelationRequest;

        private DeleteRelationRequestBuilder() {
            deleteRelationRequest = new DeleteRelationRequest();
        }

        public static DeleteRelationRequestBuilder aDeleteRelationRequest() {
            return new DeleteRelationRequestBuilder();
        }

        public DeleteRelationRequestBuilder withBaseURL(String baseURL) {
            deleteRelationRequest.setBaseURL(baseURL);
            return this;
        }

        public DeleteRelationRequestBuilder withAppId(String appId) {
            deleteRelationRequest.setAppId(appId);
            return this;
        }

        public DeleteRelationRequestBuilder withParentTypeName(String parentTypeName) {
            deleteRelationRequest.setParentTypeName(parentTypeName);
            return this;
        }

        public DeleteRelationRequestBuilder withChildTypeName(String childTypeName) {
            deleteRelationRequest.setChildTypeName(childTypeName);
            return this;
        }

        public DeleteRelationRequestBuilder withParentObjectId(String parentObjectId) {
            deleteRelationRequest.setParentObjectId(parentObjectId);
            return this;
        }

        public DeleteRelationRequestBuilder withChildObjectId(String childObjectId) {
            deleteRelationRequest.setChildObjectId(childObjectId);
            return this;
        }


        public DeleteRelationRequest build() {
            return deleteRelationRequest;
        }
    }
}
