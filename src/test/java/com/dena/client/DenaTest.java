package com.dena.client;


import com.dena.client.model.SuperClassA;
import org.junit.Test;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaTest {

    @Test
    public void saveOrUpdate() {
        Dena.initApp("denaQA");
        SuperClassA superClassA = new SuperClassA();
        superClassA.setA1(10);
        Dena.saveOrUpdate(superClassA);
    }
}