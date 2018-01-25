package com.dena.client.service.web.HttpClient.dto.request;

import com.dena.client.service.web.HttpClient.dto.Parameter;
import okhttp3.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Javad Alimohammadi Email:bc_alimohammadi@yahoo.com
 * DateTime: 4/7/13 11:23 AM
 */
public class PostRequest {
    private List<Parameter> properties = new ArrayList<>();

    private MediaType mediaType;

    private String requestBodyContent;

    private String URL;

    private Class returnType;

    private String componentName;


    public List<Parameter> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void setProperties(List<Parameter> properties) {
        this.properties = properties;
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

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getRequestBodyContent() {
        return requestBodyContent == null ? "" : requestBodyContent;
    }

    public void setRequestBodyContent(String requestBodyContent) {
        this.requestBodyContent = requestBodyContent;
    }

    public static final class ExternalComponentPostDataRequestBuilder {
        List<Parameter> properties = new ArrayList<>();
        private String URL;
        private Class returnType;
        private String componentName;
        private String requestBodyContent;
        private MediaType mediaType;

        private ExternalComponentPostDataRequestBuilder() {
        }

        public static ExternalComponentPostDataRequestBuilder anExternalComponentPostDataRequest() {
            return new ExternalComponentPostDataRequestBuilder();
        }

        public ExternalComponentPostDataRequestBuilder withProperty(String name, String value) {
            Parameter parameter = new Parameter();
            parameter.setName(name);
            parameter.setValue(value);
            properties.add(parameter);
            return this;

        }

        public ExternalComponentPostDataRequestBuilder withURL(String URL) {
            this.URL = URL;
            return this;
        }

        public ExternalComponentPostDataRequestBuilder withReturnType(Class returnType) {
            this.returnType = returnType;
            return this;
        }

        public ExternalComponentPostDataRequestBuilder withComponentName(String componentName) {
            this.componentName = componentName;
            return this;
        }

        public ExternalComponentPostDataRequestBuilder withRequestBodyContent(String requestBodyContent) {
            this.requestBodyContent = requestBodyContent;
            return this;
        }

        public ExternalComponentPostDataRequestBuilder withMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public PostRequest build() {
            PostRequest postRequest = new PostRequest();
            postRequest.setProperties(properties);
            postRequest.setURL(URL);
            postRequest.setReturnType(returnType);
            postRequest.setComponentName(componentName);
            postRequest.setRequestBodyContent(requestBodyContent);
            postRequest.setMediaType(mediaType);
            return postRequest;
        }
    }
}
