package com.kingpulse;

import com.sun.jna.Structure;
import com.sun.jna.platform.unix.X11;

/**
 * C struct in xdo.h
 * Author: Edwin Heerschap
 */
@Structure.FieldOrder({
        "xdpy",
        "charcodes",
        "charcodes_len",
        "keycode_high",
        "keycode_low",
        "keysyms_per_keycode",
        "close_display_when_freed",
        "quiet",
        "debug",
        "features_mask"
})
public class xdo_t extends Structure {

    public X11.Display xdpy;

    public charcodemap_t charcodes;

    public int charcodes_len;

    public int keycode_high;

    public int keycode_low;

    public int keysyms_per_keycode;

    public int close_display_when_freed;

    public int quiet;

    public int debug;

    public int features_mask;

    public xdo_t() {}

}
