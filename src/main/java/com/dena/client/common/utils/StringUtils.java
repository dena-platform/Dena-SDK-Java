package com.dena.client.common.utils;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public final class StringUtils {
    /**
     * Return true if string is null, empty("") or contain only space.
     *
     * @param s the string to check
     * @return
     */
    public static boolean isBlank(String s) {
        int strLen;

        if (s == null || (strLen = s.trim().length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isNoneBlank(String... s) {
        for (String str : s) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyBlank(String... s) {
        return !isNoneBlank(s);
    }
}
