package com.dena.client.service.web.HttpClient.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.peykasa.common.dto.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class ExternalComponentFetchRequest {

    List<Property> properties = new ArrayList<>();

    private String URL;

    private Class returnType;

    private TypeReference typeReference;

    private String componentName;

    public ExternalComponentFetchRequest withURL(String URL) {
        this.URL = URL;
        return this;
    }

    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public ExternalComponentFetchRequest withProperty(String name, String value) {
        Property property = new Property();
        property.setName(name);
        property.setValue(value);

        properties.add(property);

        return this;

    }

    public ExternalComponentFetchRequest withComponentName(String componentName) {
        setComponentName(componentName);
        return this;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public <T> ExternalComponentFetchRequest withReturnType(Class<T> classType) {
        setReturnType(classType);
        return this;
    }
    public <T> ExternalComponentFetchRequest withReturnType(TypeReference typeReference) {
        setReturnType(typeReference);
        return this;
    }


    public Class getReturnType() {
        return returnType;
    }

    public TypeReference getTypeReference() {
        return typeReference;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
    public void setReturnType(TypeReference returnType) {
        this.typeReference = returnType;
    }
}
