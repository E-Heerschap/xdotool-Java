package com.kingpulse;

import com.sun.jna.Structure;
import com.sun.jna.platform.unix.X11;

/**
 * C struct in xdo.h
 * Author: Edwin Heerschap
 */
@Structure.FieldOrder({
        "key",
        "code",
        "symbol",
        "group",
        "modmask",
        "needs_binding"
})
public class charcodemap_t extends Structure {

    /*
    Struct Values
     */

    public char key;

    public byte code; //Defined in X.h

    public X11.KeySym symbol;

    public int group;

    public int modmask;

    public int needs_binding;

    public charcodemap_t() {}

    public static class ByReference extends charcodemap_t implements Structure.ByReference{
        public ByReference() {
            super();
        }
    }

}
