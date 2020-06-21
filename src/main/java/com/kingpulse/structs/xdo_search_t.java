package com.kingpulse.structs;

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

    //TODO switch these enums to static definitions

    /**
     * Used for the 'require' field in xdo_search_t.
     * See xdo.h.
     */
    public enum require {

        SEARCH_ANY(0),
        SEARCH_ALL(1);


        private int value;

        private require(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }

    }

    /**
     * Used for the 'searchmask' field in xdo_search_t
     */
    public enum searchmask {

        SEARCH_TITLE(1L << 0),
        SEARCH_CLASS(1L << 1),
        SEARCH_NAME(1L << 2),
        SEARCH_PID(1L << 3),
        SEARCH_ONLYVISIBLE(1L << 4),
        SEARCH_SCREEN(1L << 5),
        SEARCH_CLASSNAME(1L << 6),
        SEARCH_DESKTOP(1L << 7);

        private long value;

        private searchmask(long val) {
            this.value = val;
        }

        public long getValue() {
            return this.value;
        }

    }


}
