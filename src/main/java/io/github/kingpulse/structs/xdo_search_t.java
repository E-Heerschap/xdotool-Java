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

    public xdo_search_t() {
    }

    /**
     * Used for the 'require' field in xdo_search_t.
     */
    public static final int require_SEARCH_ANY = 0;

    public static final int require_SEARCH_ALL = 1;

    /**
     * Used for the 'searchmask' field in xdo_search_t
     */
    public static final long searchmask_SEARCH_TITLE = 1L << 0;

    public static final long searchmask_SEARCH_CLASS = 1L << 1;

    public static final long searchmask_SEARCH_NAME = 1L << 2;

    public static final long searchmask_SEARCH_PID = 1L << 3;

    public static final long searchmask_SEARCH_ONLYVISIBLE = 1L << 4;

    public static final long searchmask_SEARCH_SCREEN = 1L << 5;

    public static final long searchmask_SEARCH_CLASSNAME = 1L << 6;

    public static final long searchmask_SEARCH_DESKTOP = 1L << 7;

}
