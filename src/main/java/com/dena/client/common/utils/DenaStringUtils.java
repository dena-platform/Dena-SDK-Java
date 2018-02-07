package com.dena.client.common.utils;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public final class DenaStringUtils {
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isNoneBlank(String ...s) {
        for (String str : s) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }
}
