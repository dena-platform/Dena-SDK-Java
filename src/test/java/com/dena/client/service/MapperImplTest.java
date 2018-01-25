package com.dena.client.service;

import com.dena.client.model.SuperClassA;
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
    public void findAllFields() {
        SuperClassA superClassA = new SuperClassA();
        superClassA.setA1(1);
        superClassA.setA2("javad");
        Map<String, Object> allFields = denaMapper.findAllFields(superClassA);
        System.out.println(allFields);
    }
}