package com.dena.client;


import com.dena.client.model.ModelWithObjectIdField;
import com.dena.client.model.SuperClassA;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaTest {

    @BeforeClass
    public static void setup() {
        Dena.initApp("denaQA");
    }

    @Test
    public void test_saveObject() {

        // create related object
        ModelWithObjectIdField modelWithObjectIdField1 = new ModelWithObjectIdField();
        ModelWithObjectIdField modelWithObjectIdField2 = new ModelWithObjectIdField();
        modelWithObjectIdField1 = Dena.saveOrUpdate(modelWithObjectIdField1);
        modelWithObjectIdField2 = Dena.saveOrUpdate(modelWithObjectIdField2);

        // create main object
        SuperClassA superClassA = new SuperClassA();
        superClassA.setA1(10);
        superClassA.setA2("javad");
        superClassA.nameList = Arrays.asList("javad", "ali");

        Map<Integer, Integer> myMap = new HashMap<>();
        myMap.put(1, 1);
        myMap.put(2, 2);
        myMap.put(3, 3);

        superClassA.myMap = myMap;
        superClassA.modelWithObjectIdFieldRelation.addRelatedObject(modelWithObjectIdField1);
        superClassA.modelWithObjectIdFieldRelation.addRelatedObject(modelWithObjectIdField2);

        Dena.saveOrUpdate(superClassA);
    }

    @Test
    public void test_updateObject() {
        // create new object
        ModelWithObjectIdField modelWithObjectIdField1 = new ModelWithObjectIdField();
        modelWithObjectIdField1 = Dena.saveOrUpdate(modelWithObjectIdField1);

        modelWithObjectIdField1.setA2(45);
        modelWithObjectIdField1 = Dena.saveOrUpdate(modelWithObjectIdField1);


    }

    @Test
    public void test_DeleteOneObject_When_Object_Exist() {
        // create new object
        ModelWithObjectIdField modelWithObjectIdField1 = new ModelWithObjectIdField();
        modelWithObjectIdField1 = Dena.saveOrUpdate(modelWithObjectIdField1);

        long actualDeletedCount = Dena.remove(modelWithObjectIdField1);
        assertEquals("Number of deleted object is wrong", 1, actualDeletedCount);

    }

    @Test
    public void test_DeleteBulkObject_When_Objects_Exist() {
        // create new objects
        ModelWithObjectIdField modelWithObjectIdField1 = new ModelWithObjectIdField();
        ModelWithObjectIdField modelWithObjectIdField2 = new ModelWithObjectIdField();
        ModelWithObjectIdField modelWithObjectIdField3 = new ModelWithObjectIdField();
        modelWithObjectIdField1 = Dena.saveOrUpdate(modelWithObjectIdField1);
        modelWithObjectIdField2 = Dena.saveOrUpdate(modelWithObjectIdField2);
        modelWithObjectIdField3 = Dena.saveOrUpdate(modelWithObjectIdField3);

        Set<ModelWithObjectIdField> modelWithObjectIdFields = new HashSet<>();
        modelWithObjectIdFields.add(modelWithObjectIdField1);
        modelWithObjectIdFields.add(modelWithObjectIdField2);
        modelWithObjectIdFields.add(modelWithObjectIdField3);

        long actualDeletedCount = Dena.removeBulk(modelWithObjectIdFields);
        assertEquals("Number of deleted object is wrong", 3, actualDeletedCount);

    }


}