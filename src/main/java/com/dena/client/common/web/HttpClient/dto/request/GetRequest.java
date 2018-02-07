package com.dena.client.common.web.HttpClient.dto.request;

import com.dena.client.common.web.HttpClient.dto.Parameter;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class GetRequest {

    private List<Parameter> parameterList = new ArrayList<>();

    private String URL;

    private Class returnType;

    private TypeReference typeReference;

    private String componentName;

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public TypeReference getTypeReference() {
        return typeReference;
    }

    public void setTypeReference(TypeReference typeReference) {
        this.typeReference = typeReference;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }


    public static final class GetRequestBuilder {
        private GetRequest getRequest;

        private GetRequestBuilder() {
            getRequest = new GetRequest();
        }

        public static GetRequestBuilder aGetRequest() {
            return new GetRequestBuilder();
        }

        public GetRequestBuilder withParameterList(List<Parameter> parameterList) {
            getRequest.setParameterList(parameterList);
            return this;
        }

        public GetRequestBuilder withURL(String URL) {
            getRequest.setURL(URL);
            return this;
        }

        public GetRequestBuilder withReturnType(Class returnType) {
            getRequest.setReturnType(returnType);
            return this;
        }

        public GetRequestBuilder withTypeReference(TypeReference typeReference) {
            getRequest.setTypeReference(typeReference);
            return this;
        }

        public GetRequestBuilder withComponentName(String componentName) {
            getRequest.setComponentName(componentName);
            return this;
        }

        public GetRequest build() {
            return getRequest;
        }
    }
}
