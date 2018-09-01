package com.dena.client.model;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public class ChildClass {

    public String childStringField;

    public int childIntField;

    @Override
    public String toString() {
        return "ChildClass{" +
                "childStringField='" + childStringField + '\'' +
                ", childIntField=" + childIntField +
                '}';
    }
}
