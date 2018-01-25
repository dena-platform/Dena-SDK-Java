package com.dena.client.service.web.HttpClient.dto.request;

import com.dena.client.service.web.HttpClient.dto.Parameter;
import okhttp3.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class PostRequest {
    private List<Parameter> parameterList = new ArrayList<>();

    private MediaType mediaType;

    private String requestBodyContent;

    private String URL;

    private Class returnType;

    private String componentName;


    public List<Parameter> getParameterList() {
        return Collections.unmodifiableList(parameterList);
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

    public static final class PostRequestBuilder {
        List<Parameter> properties = new ArrayList<>();
        private String URL;
        private Class returnType;
        private String componentName;
        private String requestBodyContent;
        private MediaType mediaType;

        private PostRequestBuilder() {
        }

        public static PostRequestBuilder anExternalComponentPostDataRequest() {
            return new PostRequestBuilder();
        }

        public PostRequestBuilder withProperty(String name, String value) {
            Parameter parameter = new Parameter();
            parameter.setName(name);
            parameter.setValue(value);
            properties.add(parameter);
            return this;

        }

        public PostRequestBuilder withURL(String URL) {
            this.URL = URL;
            return this;
        }

        public PostRequestBuilder withReturnType(Class returnType) {
            this.returnType = returnType;
            return this;
        }

        public PostRequestBuilder withComponentName(String componentName) {
            this.componentName = componentName;
            return this;
        }

        public PostRequestBuilder withRequestBodyContent(String requestBodyContent) {
            this.requestBodyContent = requestBodyContent;
            return this;
        }

        public PostRequestBuilder withMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public PostRequest build() {
            PostRequest postRequest = new PostRequest();
            postRequest.setParameterList(properties);
            postRequest.setURL(URL);
            postRequest.setReturnType(returnType);
            postRequest.setComponentName(componentName);
            postRequest.setRequestBodyContent(requestBodyContent);
            postRequest.setMediaType(mediaType);
            return postRequest;
        }
    }
}
