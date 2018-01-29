package com.dena.client.service;

import com.dena.client.model.ModelWithAssignedObjectId;
import com.dena.client.model.SuperClassA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class MapperImplTest {
    private DenaMapper denaMapper;

    @Before
    public void setup() {
        denaMapper = new DenaMapperImpl();
    }

    @Test
    public void test_findAllFields() {
        SuperClassA superClassA = new SuperClassA();
        superClassA.setA1(1);
        superClassA.setA2("javad");
        Map<String, Object> allFields = denaMapper.findAllFields(superClassA);
        System.out.println(allFields);
    }

    @Test
    public void test_setObjectId() {
        String objectId = "5a6b15286f01790dfc600c3f";

        SuperClassA superClassA = new SuperClassA();

        SuperClassA newClassA = denaMapper.setObjectId(superClassA, objectId);
        Map<String, Object> objectMap = denaMapper.findAllFields(newClassA);

        Assert.assertEquals("Object Id is invalid", objectId, objectMap.get(DenaMapperImpl.DENA_OBJECT_ID));
    }

    @Test
    public void test_setObjectId_when_objectId_is_assigned() {
        String objectId = "5a6b24026f01790dfc600c40";

        ModelWithAssignedObjectId modelWithAssignedObjectId = new ModelWithAssignedObjectId();

        ModelWithAssignedObjectId newClassA = denaMapper.setObjectId(modelWithAssignedObjectId, objectId);
        Map<String, Object> objectMap = denaMapper.findAllFields(newClassA);

        Assert.assertEquals("Object Id is invalid", objectId, objectMap.get(DenaMapperImpl.DENA_OBJECT_ID));
    }


}