package com.dena.client.model;

import com.dena.client.core.feature.persistence.Relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class ParentClass {
    public String object_id;

    public transient int a1;

    private UserDefinedClass UserDefinedClass;

    public Map<Integer, Integer> myMap = new HashMap<>();

    private String a2;

    public String a3;

    public List<String> nameList = new ArrayList<>();

    public Relation<ChildClass> childRelation = Relation.makeRelationOf(ChildClass.class);

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }
}
