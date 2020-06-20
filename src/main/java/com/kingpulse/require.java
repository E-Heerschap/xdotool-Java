package com.kingpulse;


/**
 * Used for the 'require' field in xdo_search_t.
 * See xdo.h.
 * Author: Edwin Heerschap
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
