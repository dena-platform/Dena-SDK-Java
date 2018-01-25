package com.dena.client.service.web.HttpClient.dto.request;

import com.dena.client.service.web.HttpClient.dto.Parameter;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class FetchRequest {

    List<Parameter> properties = new ArrayList<>();

    private String URL;

    private Class returnType;

    private TypeReference typeReference;

    private String componentName;

    public FetchRequest withURL(String URL) {
        this.URL = URL;
        return this;
    }

    public List<Parameter> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void setProperties(List<Parameter> properties) {
        this.properties = properties;
    }

    public FetchRequest withProperty(String name, String value) {
        Parameter parameter = new Parameter();
        parameter.setName(name);
        parameter.setValue(value);

        properties.add(parameter);

        return this;

    }

    public FetchRequest withComponentName(String componentName) {
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

    public <T> FetchRequest withReturnType(Class<T> classType) {
        setReturnType(classType);
        return this;
    }
    public <T> FetchRequest withReturnType(TypeReference typeReference) {
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
