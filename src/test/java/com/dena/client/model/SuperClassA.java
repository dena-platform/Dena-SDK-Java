package com.dena.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class SuperClassA {
    private transient int a1;

    private String a2;

    public String a3;

    public List<Long> names = new ArrayList<>();

    public Map<Integer, Integer> sso = new HashMap<>();

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }
}
