package com.dena.client.common.web.HttpClient.dto;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public class Parameter {

    String name;
    String value;


    public Parameter() {
    }

    public Parameter(String name, String value) {
        this.value = value;
        this.name = name;
    }


    public Parameter(String name, Long value) {
        this(name, String.valueOf(value));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
