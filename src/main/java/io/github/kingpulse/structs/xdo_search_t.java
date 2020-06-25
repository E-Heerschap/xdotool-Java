package io.github.kingpulse.structs;

import com.sun.jna.Structure;


/**
 * C struct xdo_search_t in xdo.h
 * Author: Edwin Heerschap
 */
@Structure.FieldOrder({
        "title",
        "winclass",
        "winclassname",
        "winname",
        "pid",
        "max_depth",
        "only_visible",
        "screen",
        "require",
        "searchmask",
        "desktop",
        "limit"
})
public class xdo_search_t extends Structure {

    public String title;

    public String winclass;

    public String winclassname;

    public String winname;

    public int pid;

    public long max_depth;

    public int only_visible;

    public int screen;

    public int require; //require.getValue() enum

    public int searchmask;

    public long desktop;

    public int limit; //TODO implement method to convert this unsigned value if first bit set.

    /**
     * Used for the 'searchmask' field in xdo_search_t
     */
    public static final int searchmask_SEARCH_TITLE = 1 << 0;

    /**
     * Used for the 'require' field in xdo_search_t.
     */
    public static final int require_SEARCH_ANY = 0;

    public static final int require_SEARCH_ALL = 1;
    public static final int searchmask_SEARCH_CLASS = 1 << 1;
    public static final int searchmask_SEARCH_NAME = 1 << 2;
    public static final int searchmask_SEARCH_PID = 1 << 3;
    public static final int searchmask_SEARCH_ONLYVISIBLE = 1 << 4;
    public static final int searchmask_SEARCH_SCREEN = 1 << 5;
    public static final int searchmask_SEARCH_CLASSNAME = 1 << 6;
    public static final int searchmask_SEARCH_DESKTOP = 1 << 7;

    public xdo_search_t() {
        max_depth = -1; //Set 99% of the time anyway.
    }

}
