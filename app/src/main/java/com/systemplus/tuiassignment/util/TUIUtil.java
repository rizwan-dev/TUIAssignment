package com.systemplus.tuiassignment.util;

import com.tumblr.remember.Remember;

/**
 * @author Rizwanul Haque
 */

public class TUIUtil {
    public static String getCategoryToExclude() {
        boolean isExludeChecked = Remember.getBoolean(SPConstants.IS_EXCLUDE_EXPLICIT, false);
        if (isExludeChecked) {
            return TUIConstants.EXCLUDED_CATEGORIES;
        } else {
            return null;
        }
    }
}
