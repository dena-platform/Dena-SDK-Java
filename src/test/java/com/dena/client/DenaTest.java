package com.dena.client;


import com.dena.client.model.SuperClassA;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

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

        Dena.saveOrUpdate(superClassA);
    }
}