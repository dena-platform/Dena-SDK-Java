package com.dena.client;


import com.dena.client.model.SuperClassA;
import com.dena.client.service.DenaMapperImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

        Dena.saveOrUpdate(superClassA);
        superClassA.setA2("javad");

        Dena.saveOrUpdate(superClassA);
    }
}