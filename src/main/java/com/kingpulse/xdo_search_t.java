package com.kingpulse;

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

}
