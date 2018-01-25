package com.dena.client;

import com.dena.client.service.DenaMapperImpl;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class Dena {
    private static String Dena_URL = "http://localhost:8090";

    private final static DenaMapperImpl mapper = new DenaMapperImpl();

    public static void setEndPoint(String endPoint) {
        Dena_URL = endPoint;
    }

    public static <T> T saveOrUpdate(T object) {
        System.out.println(mapper.findAllFields(object));

        return null;
    }
}
