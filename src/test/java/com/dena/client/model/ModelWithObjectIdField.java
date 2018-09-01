package com.dena.client.model;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public class ModelWithObjectIdField {
    public String object_id;

    private int a1;
    private int a2;


    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    @Override
    public String toString() {
        return "ModelWithObjectIdField{" +
                "object_id='" + object_id + '\'' +
                ", a1=" + a1 +
                ", a2=" + a2 +
                '}';
    }
}
