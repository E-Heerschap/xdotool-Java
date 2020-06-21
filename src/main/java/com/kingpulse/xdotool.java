package com.kingpulse;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

//TODO Add convert from useconds_t (unsigned 32 bit native integer) to java int.

/**
 * Maps the libxdo
 * Author: Edwin Heerschap
 */
public interface xdotool extends Library {

    xdotool lib = (xdotool) Native.load("xdo", xdotool.class);

    public xdo_t xdo_new(final String display);

    xdo_t xdo_new_with_opened_display(X11.Display xdpy,
                                      final String display,
                                      int close_display_when_freed);

    String xdo_version();

    void xdo_free(xdo_t xdo);

    int xdo_move_mouse(final xdo_t xdo, int x, int y, int screen);

    int xdo_move_mouse_relative_to_window(final xdo_t xdo, X11.Window window, int x, int y);

    int xdo_move_mouse_relative(final xdo_t xdo, int x, int y);

    int xdo_mouse_up(final xdo_t xdo, X11.Window window, int button);

    int xdo_get_mouse_location(final xdo_t xdo, IntByReference x, IntByReference y, IntByReference screen_num);

    int xdo_get_window_at_mouse(final xdo_t xdo, LongByReference window_ret);

    int xdo_get_mouse_location2(final xdo_t xdo, IntByReference x_ret,
                                IntByReference y_ret, IntByReference screen_num_ret,
                                LongByReference window_ret);

    int xdo_wait_for_mouse_move_from(final xdo_t xdo, int origin_x, int origin_y);

    int xdo_wait_for_mouse_move_to(final xdo_t xdo, int dest_x, int dest_y);

    int xdo_click_window(final xdo_t xdo, X11.Window window, int button);

    //Delay in this is useconds_t type. Unsigned 32 bit integer.
    int xdo_click_window_multiple(final xdo_t xdo, X11.Window window, int button,
                                  int repeat, int delay);

    int xdo_enter_text_window(final xdo_t xdo, X11.Window window, String string, int delay);

    int xdo_send_keysequence_window(final xdo_t xdo, X11.Window window, final String keysequence, int delay);

    int xdo_second_keysequence_window_up(final xdo_t xdo, X11.Window window, final String keysequence, int delay);

    int xdo_send_keysequence_window_down(final xdo_t xdo, X11.Window window, final String keysequence, int delay);

    int xdo_send_keysequence_window_list_do(final xdo_t xdo, X11.Window window, charcodemap_t keys, int nkeys,
                                            int pressed, IntByReference modifier, int delay);
}

