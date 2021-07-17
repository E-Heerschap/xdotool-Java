package io.github.kingpulse;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import io.github.kingpulse.structs.charcodemap_t;
import io.github.kingpulse.structs.xdo_search_t;
import io.github.kingpulse.structs.xdo_t;

//TODO Add convert from useconds_t (unsigned 32 bit native integer) to java int.

//TODO Switch classes in definitions package to 'native types' that map back and forth

/**
 * Maps the libxdo.
 * Best documentation is available by looking
 * at xdotool repository:
 * https://github.com/jordansissel/xdotool/
 * Author: Edwin Heerschap
 */
public interface xdotool extends Library {

    /**
     * Attempts to load the xdotool library.
     *
     * @param name Name of library. Do not include OS specific
     *             prefix or suffix. I.e libxdo.so is passed as xdo.
     * @return loaded xdotool library
     */
    static xdotool loadLib(String name) {
        return Native.load("name", xdotool.class);
    }

    /**
     * Attempts to load the xdotool library from either
     * 'xdotool.name' system property or uses 'xdo'.
     *
     * @return loaded xdotool library
     */
    static xdotool loadLib() {

        String name = System.getProperty("xdotool.name");

        if (name == null) {
            return Native.load("xdo", xdotool.class);
        } else {
            return Native.load(name, xdotool.class);
        }

    }

    xdo_t xdo_new(final String display);

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

    int xdo_get_window_at_mouse(final xdo_t xdo, X11.WindowByReference window_ret);

    int xdo_get_mouse_location2(final xdo_t xdo, IntByReference x_ret,
                                IntByReference y_ret, IntByReference screen_num_ret,
                                LongByReference window_ret);

    int xdo_wait_for_mouse_move_from(final xdo_t xdo, int origin_x, int origin_y);

    int xdo_wait_for_mouse_move_to(final xdo_t xdo, int dest_x, int dest_y);

    int xdo_click_window(final xdo_t xdo, X11.Window window, int button);

    //Delay in this is useconds_t type. Unsigned 32 bit integer.
    int xdo_click_window_multiple(final xdo_t xdo, X11.Window window, int button, int repeat, int delay);

    int xdo_enter_text_window(final xdo_t xdo, X11.Window window, String string, int delay);

    int xdo_send_keysequence_window(final xdo_t xdo, X11.Window window, final String keysequence, int delay);

    int xdo_send_keysequence_window_up(final xdo_t xdo, X11.Window window, final String keysequence, int delay);

    int xdo_send_keysequence_window_down(final xdo_t xdo, X11.Window window, final String keysequence, int delay);

    int xdo_send_keysequence_window_list_do(final xdo_t xdo, X11.Window window, charcodemap_t keys, int nkeys,
                                            int pressed, IntByReference modifier, int delay);

    //TODO Create enum mapping states here
    int xdo_wait_for_window_map_state(final xdo_t xdo, X11.Window wid, int map_state);

    //These are unsigned ints
    int xdo_wait_for_window_size(final xdo_t xdo, X11.Window window, int width, int height, int flags, int to_or_from);

    int xdo_move_window(final xdo_t xdo, X11.Window wid, int x, int y);

    /*
    //TODO Can we make a class mapping to and from native unsigned int? Maybe.
     */

    //Unsigned ints here :)
    int xdo_translate_window_with_sizehint(final xdo_t xdo, X11.Window window, int width, int height,
                                           IntByReference width_ret, IntByReference height_ret);

    int xdo_set_window_size(final xdo_t xdo, X11.Window wid, int w, int h, int flags);

    int xdo_set_window_property(final xdo_t xdo, X11.Window wid, String property, String value);

    int xdo_set_window_class(final xdo_t xdo, X11.Window wid, String name);

    int xdo_set_window_urgency(final xdo_t xdo, X11.Window wid, int urgency);

    int xdo_set_window_override_redirect(final xdo_t xdo, X11.Window wid, int override_redirect);

    int xdo_focus_window(final xdo_t xdo, X11.Window wid);

    int xdo_raise_window(final xdo_t xdo, X11.Window wid);

    int xdo_get_focused_window(final xdo_t xdo, X11.WindowByReference window_ret);

    int xdo_wait_for_window_focus(final xdo_t xdo, X11.Window window, int want_focus);

    int xdo_get_pid_window(final xdo_t xdo, X11.Window window);

    int xdo_get_focused_window_sane(final xdo_t xdo, X11.WindowByReference window_ret);

    int xdo_activate_window(final xdo_t xdo, X11.Window wid);

    int xdo_wait_for_window_active(final xdo_t xdo, X11.Window window, int active);

    int xdo_map_window(final xdo_t xdo, X11.Window wid);

    int xdo_unmap_window(final xdo_t xdo, X11.Window window);

    int xdo_minimize_window(final xdo_t xdo, X11.Window wid);

    //Use WindowStateAction.getAction() for action numbers. Also why is this a long?
    int xdo_window_state(xdo_t xdo, X11.Window window, long action, String property);

    int xdo_reparent_window(final xdo_t xdo, X11.Window wid_source, X11.Window wid_target);

    /**
     * VALUES RETURNED BY THIS ARE INACCURATE AS OF VERSION 3.20200619.1 (CURRENT).
     * Use XFacade getWindowLocation method instead.
     *
     * @param xdo
     * @param wid
     * @param x_ret
     * @param y_ret
     * @param screen_ret
     * @return
     */
    int xdo_get_window_location(final xdo_t xdo, X11.Window wid, IntByReference x_ret, IntByReference y_ret,
                                PointerByReference screen_ret);

    int xdo_get_window_size(final xdo_t xdo, X11.Window wid, IntByReference width_ret, IntByReference height_ret);

    int xdo_get_active_window(final xdo_t xdo, X11.WindowByReference window_ret);

    int xdo_select_window_with_click(final xdo_t xdo, X11.WindowByReference window_ret);

    int xdo_set_number_of_desktops(final xdo_t xdo, long ndesktops);

    int xdo_get_number_of_desktops(final xdo_t xdo, LongByReference ndesktops);

    int xdo_set_current_desktop(final xdo_t xdo, long desktop);

    int xdo_get_current_desktop(final xdo_t xdo, LongByReference desktop);

    int xdo_set_desktop_for_window(final xdo_t xdo, X11.Window wid, long desktop);

    int xdo_get_desktop_for_window(final xdo_t xdo, X11.Window wid, LongByReference desktop);

    int xdo_search_windows(final xdo_t xdo, final xdo_search_t search, PointerByReference windowlist_ret,
                           IntByReference nwindows_ret);

    String xdo_get_window_property_by_atom(final xdo_t xdo, X11.Window window, X11.Atom atom, LongByReference nitems,
                                           X11.AtomByReference type, IntByReference size);

    int xdo_get_window_property(final xdo_t xdo, X11.Window window, String property, PointerByReference value,
                                LongByReference nitems, X11.AtomByReference type, IntByReference size);

    //mask
    int xdo_get_input_state(final xdo_t xdo);

    PointerByReference xdo_get_symbol_map();

    int xdo_get_active_modifiers(final xdo_t xdo, PointerByReference keys, IntByReference nkeys);

    int xdo_clear_active_modifiers(final xdo_t xdo, X11.Window window, charcodemap_t active_mods, int active_mods_n);

    int xdo_set_active_modifiers(final xdo_t xdo, X11.Window window, charcodemap_t active_mods, int active_mods_n);

    int xdo_get_desktop_viewport(final xdo_t xdo, IntByReference x_ret, IntByReference y_ret);

    int xdo_set_desktop_viewport(final xdo_t xdo, int x, int y);

    int xdo_kill_window(final xdo_t xdo, X11.Window window);

    int xdo_close_window(final xdo_t xdo, X11.Window window);

    int xdo_find_window_client(final xdo_t xdo, X11.Window window,
                               X11.WindowByReference window_ret, int direction);

    int xdo_get_window_name(final xdo_t xdo, X11.Window window, PointerByReference name_ret,
                            IntByReference name_len_ret, IntByReference name_type);

    void xdo_disable_feature(xdo_t xdo, int feature);

    void xdo_enable_feature(xdo_t xdo, int feature);

    int xdo_has_feature(xdo_t xdo, int feature);

    int xdo_get_viewport_dimensions(xdo_t xdo, IntByReference width,
                                    IntByReference height, int screen);
}

