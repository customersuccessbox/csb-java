package com.customersuccessbox.sdk.utils;

/**
 * The Class CSBUtils.
 */
public class CSBUtils {

    /**
     * Empty check.
     *
     * @param str the str
     * @return true, if successful
     */
    public static boolean emptyCheck(final String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return true;
    }

}
