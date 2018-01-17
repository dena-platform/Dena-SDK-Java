package com.dena.client;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */

public final class Dena {
    private static String Dena_URL = "http://localhost:8090";

    public static void setEndPoint(String endPoint) {
        Dena_URL = endPoint;
    }

    public static  <T> T saveOrUpdate(T object) {
        try {
            System.out.println(object.getClass().getConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
