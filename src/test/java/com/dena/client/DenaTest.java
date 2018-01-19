package com.dena.client;


import com.dena.client.model.A;
import org.junit.Test;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaTest {

    @Test
    public void saveOrUpdate() {
        Dena.saveOrUpdate(new A(1));
    }
}