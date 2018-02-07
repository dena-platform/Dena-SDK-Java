package com.dena.client;


import com.dena.client.model.SuperClassA;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaTest {

    @BeforeClass
    public static void setup() {
        Dena.initApp("denaQA");
    }

    @Test
    public void test_saveOrUpdate() {
        SuperClassA superClassA = new SuperClassA();
        superClassA.setA1(10);
        superClassA.setA2("javad");
        superClassA.names = Arrays.asList("javad", "ali");
        Map<Integer, Integer> sso = new HashMap<>();
        sso.put(1, 1);
        sso.put(2, 2);
        sso.put(3, 3);
        superClassA.sso = sso;

        Dena.saveOrUpdate(superClassA);
    }
}