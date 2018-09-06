package com.dena.client.common.utils;

/**
 * @author Javad Alimohammadi<bs.alimohammadi@yahoo.com>
 */

public final class URLUtils {
    public static String makeURLString(String... urls) {
        StringBuilder finalURL = new StringBuilder(50);

        for (String url : urls) {

            if (url.startsWith("http")) {
                finalURL.append(url);

            } else {
                if (!url.startsWith("/")) {
                    finalURL.append('/')
                            .append(url);
                } else {
                    finalURL.append(url);
                }
            }
        }


        return finalURL.toString();
    }
}
