package com.kingpulse;

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
