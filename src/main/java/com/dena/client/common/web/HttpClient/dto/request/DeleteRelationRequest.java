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

    private String typeName1;

    private String objectId1;

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

    public String getTypeName1() {
        return typeName1;
    }

    public void setTypeName1(String typeName1) {
        if (!typeName1.startsWith("/")) {
            typeName1 = "/" + typeName1;
        }

        this.typeName1 = typeName1;
    }

    public String getObjectId1() {
        return objectId1;
    }

    public void setObjectId1(String objectId1) {
        if (!objectId1.startsWith("/")) {
            objectId1 = "/" + objectId1;
        }

        this.objectId1 = objectId1;
    }

    public static final class DeleteRelationRequestBuilder {
        private DeleteRelationRequest createObjectRequest;

        private DeleteRelationRequestBuilder() {
            createObjectRequest = new DeleteRelationRequest();
        }

        public static DeleteRelationRequestBuilder aDeleteObjectRequest() {
            return new DeleteRelationRequestBuilder();
        }

        public DeleteRelationRequestBuilder withBaseURL(String baseURL) {
            createObjectRequest.setBaseURL(baseURL);
            return this;
        }

        public DeleteRelationRequestBuilder withAppId(String appId) {
            createObjectRequest.setAppId(appId);
            return this;
        }


        public DeleteRelationRequestBuilder withTypeName1(String typeName) {
            createObjectRequest.setTypeName1(typeName);
            return this;
        }

        public DeleteRelationRequestBuilder withObjectId1(String objectId) {
            if (StringUtils.isBlank(createObjectRequest.getObjectId1())) {
                createObjectRequest.setObjectId1(objectId);
            } else {
                String newObjectId = createObjectRequest.getObjectId1() + "," + objectId;
                createObjectRequest.setObjectId1(newObjectId);
            }

            return this;
        }

        public DeleteRelationRequestBuilder withParameterList(List<Parameter> parameterList) {
            createObjectRequest.setParameterList(parameterList);
            return this;
        }

        public DeleteRelationRequestBuilder withParameterList(Map<String, Object> parameterMap) {
            if (MapUtils.isNotEmpty(parameterMap)) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    createObjectRequest.parameterList.add(new Parameter(entry.getKey(), entry.getValue().toString()));
                }
            }
            return this;
        }


        public DeleteRelationRequest build() {
            return createObjectRequest;
        }
    }
}
