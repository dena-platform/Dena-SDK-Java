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

public class DeleteObjectRequest {
    private List<Parameter> parameterList = new ArrayList<>();

    private String BaseURL;

    private String appId;

    private String typeName;

    private String objectIds;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        if (!typeName.startsWith("/")) {
            typeName = "/" + typeName;
        }

        this.typeName = typeName;
    }

    public String getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String objectIds) {
        if (!objectIds.startsWith("/")) {
            objectIds = "/" + objectIds;
        }

        this.objectIds = objectIds;
    }

    public static final class DeleteObjectRequestBuilder {
        private DeleteObjectRequest createObjectRequest;

        private DeleteObjectRequestBuilder() {
            createObjectRequest = new DeleteObjectRequest();
        }

        public static DeleteObjectRequestBuilder aDeleteObjectRequest() {
            return new DeleteObjectRequestBuilder();
        }

        public DeleteObjectRequestBuilder withBaseURL(String baseURL) {
            createObjectRequest.setBaseURL(baseURL);
            return this;
        }

        public DeleteObjectRequestBuilder withAppId(String appId) {
            createObjectRequest.setAppId(appId);
            return this;
        }


        public DeleteObjectRequestBuilder withTypeName(String typeName) {
            createObjectRequest.setTypeName(typeName);
            return this;
        }

        public DeleteObjectRequestBuilder withObjectId(String objectId) {
            if (StringUtils.isBlank(createObjectRequest.getObjectIds())) {
                createObjectRequest.setObjectIds(objectId);
            } else {
                String newObjectId = createObjectRequest.getObjectIds() + "," + objectId;
                createObjectRequest.setObjectIds(newObjectId);
            }

            return this;
        }

        public DeleteObjectRequestBuilder withParameterList(List<Parameter> parameterList) {
            createObjectRequest.setParameterList(parameterList);
            return this;
        }

        public DeleteObjectRequestBuilder withParameterList(Map<String, Object> parameterMap) {
            if (MapUtils.isNotEmpty(parameterMap)) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    createObjectRequest.parameterList.add(new Parameter(entry.getKey(), entry.getValue().toString()));
                }
            }
            return this;
        }


        public DeleteObjectRequest build() {
            return createObjectRequest;
        }
    }
}
