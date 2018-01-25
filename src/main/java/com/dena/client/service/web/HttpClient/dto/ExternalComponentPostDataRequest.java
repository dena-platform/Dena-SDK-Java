package com.dena.client.service.web.HttpClient.dto;

import ir.peykasa.common.dto.Property;
import okhttp3.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Javad Alimohammadi Email:bc_alimohammadi@yahoo.com
 * DateTime: 4/7/13 11:23 AM
 */
public class ExternalComponentPostDataRequest {
    private List<Property> properties = new ArrayList<>();

    private MediaType mediaType;

    private String requestBodyContent;

    private String URL;

    private Class returnType;

    private String componentName;


    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void setProperties(List<Property> properties) {
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
        List<Property> properties = new ArrayList<>();
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
            Property property = new Property();
            property.setName(name);
            property.setValue(value);
            properties.add(property);
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

        public ExternalComponentPostDataRequest build() {
            ExternalComponentPostDataRequest externalComponentPostDataRequest = new ExternalComponentPostDataRequest();
            externalComponentPostDataRequest.setProperties(properties);
            externalComponentPostDataRequest.setURL(URL);
            externalComponentPostDataRequest.setReturnType(returnType);
            externalComponentPostDataRequest.setComponentName(componentName);
            externalComponentPostDataRequest.setRequestBodyContent(requestBodyContent);
            externalComponentPostDataRequest.setMediaType(mediaType);
            return externalComponentPostDataRequest;
        }
    }
}
