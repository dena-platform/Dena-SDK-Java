package com.dena.client;


import com.dena.client.model.ChildClass;
import com.dena.client.model.ModelWithObjectIdField;
import com.dena.client.model.ParentClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public class DenaTest {

    @BeforeClass
    public static void setup() {
        Dena.initApp("denaQA");
    }

    @Test
    public void test_saveObject() {

//        // create related object
//        ModelWithObjectIdField modelWithObjectIdField1 = new ModelWithObjectIdField();
//        ModelWithObjectIdField modelWithObjectIdField2 = new ModelWithObjectIdField();
//        modelWithObjectIdField1 = Dena.saveOrUpdate(modelWithObjectIdField1);
//        modelWithObjectIdField2 = Dena.saveOrUpdate(modelWithObjectIdField2);
//
//        // create main object
//        ParentClass parentClass = new ParentClass();
//        parentClass.setA1(10);
//        parentClass.setA2("javad");
//        parentClass.nameList = Arrays.asList("javad", "ali");
//
//        Map<Integer, Integer> myMap = new HashMap<>();
//        myMap.put(1, 1);
//        myMap.put(2, 2);
//        myMap.put(3, 3);
//
//        parentClass.myMap = myMap;
//        parentClass.childClass.addObject(modelWithObjectIdField1);
//        parentClass.childClass.addObject(modelWithObjectIdField2);
//
//        Dena.saveOrUpdate(parentClass);
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

        long actualDeletedCount = Dena.removeObject(modelWithObjectIdField1);
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

        long actualDeletedCount = Dena.removeObjects(modelWithObjectIdFields);
        assertEquals("Number of deleted object is wrong", 3, actualDeletedCount);

    }


    @Test
    public void test_DeleteRelationWithType_When_Type_Exist() {
        ChildClass childObject = new ChildClass();
        childObject.childIntField = 1;
        childObject.childStringField = "string value 1";

        childObject = Dena.saveOrUpdate(childObject);

        ParentClass parentObject = new ParentClass();
        parentObject.childRelation.addObject(childObject);

        parentObject = Dena.saveOrUpdate(parentObject);

        Dena.removeRelation(parentObject, parentObject.childRelation);

    }

    @Test
    public void test_DeleteRelationWithObjectId_When_Type_Exist() {
        ChildClass childObject = new ChildClass();
        childObject.childIntField = 1;
        childObject.childStringField = "string value 1";

        childObject = Dena.saveOrUpdate(childObject);

        ParentClass parentObject = new ParentClass();
        parentObject.childRelation.addObject(childObject);

        parentObject = Dena.saveOrUpdate(parentObject);

        Dena.removeRelation(parentObject, parentObject.childRelation, childObject);

    }

    @Test
    public void test_FindObjectWithObjectId_When_Object_Exist() {
        ChildClass childObject = new ChildClass();
        childObject.childIntField = 1;
        childObject.childStringField = "string value 1";

        childObject = Dena.saveOrUpdate(childObject);

        ParentClass parentObject = new ParentClass();
        parentObject.childRelation.addObject(childObject);

        parentObject = Dena.saveOrUpdate(parentObject);

        parentObject = Dena.findById(ParentClass.class, parentObject.object_id);

    }

    @Test
    public void test_LoadRelatedObjects_When_Object_Exist() {
        ChildClass childObject1 = new ChildClass();
        childObject1.childIntField = 1;
        childObject1.childStringField = "string value 1";

        ChildClass childObject2 = new ChildClass();
        childObject2.childIntField = 10;
        childObject2.childStringField = "string value 1";

        childObject1 = Dena.saveOrUpdate(childObject1);
        childObject2 = Dena.saveOrUpdate(childObject2);

        ParentClass parentObject = new ParentClass();
        parentObject.childRelation.addObject(childObject1);
        parentObject.childRelation.addObject(childObject2);

        parentObject = Dena.saveOrUpdate(parentObject);

        List<ChildClass> childClasses = Dena.loadRelation(parentObject, parentObject.childRelation);

        System.out.println(childClasses);

    }


}