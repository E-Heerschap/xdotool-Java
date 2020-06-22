package io.github.kingpulse;

import com.sun.jna.Native;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.tools.javac.util.Pair;
import io.github.kingpulse.structs.xdo_search_t;
import io.github.kingpulse.structs.xdo_t;
import lombok.Getter;
import lombok.Setter;

public class LibxdoFacade {

    private xdotool lib;

    /**
     * Maximum padding in x direction for a window. Window manager imposed border
     */
    @Setter @Getter
    private int maxXPad = 10;

    /**
     * Maximum padding in y direction for a window. Window manager imposed border
     */
    @Setter @Getter
    private int maxYPad = 50;

    public LibxdoFacade(xdotool lib) {
        this.lib = lib;
    }

    /**
     * Performs the xdo_search_windows function and performs required conversions.
     * @param xdo xdo_t instance
     * @param search search structure defining the search
     * @return Array of X11 windows matching the search
     */
    public X11.Window[] xdoSearchWindows(final xdo_t xdo, final xdo_search_t search) {

        PointerByReference windows = new PointerByReference();
        IntByReference numWindows = new IntByReference();

        lib.xdo_search_windows(xdo, search, windows, numWindows);
        X11.Window[] windowArr = new X11.Window[numWindows.getValue()];

        for(int i = 0; i < numWindows.getValue(); i++) {
            X11.Window win = new X11.Window(windows.getValue().getLong(i * X11.Window.SIZE));
            windowArr[i] = win;
        }

        return windowArr;

    }

    /**
     * Blocks until the window passed window has been moved/considered synced.
     * IMPORTANT: If moving window less than getMaxXPad in x direction and getMaxYPad in y direction, this function
     * is less efficient than directly calling xdo_move_window and cannot guarantee the sync.
     * The sync status is calculated by calculating difference in
     * the current window position to new position. X Window manager imposed borders remove the identity mapping from
     * passed x,y coordinates to the realised coordinates.
     * Due to this the tolerance for these calculations is getMaxXPad and getMaxYPad for the x,y directions.
     * @param xdo xdo_t instance
     * @param window Window to move
     * @param x X coordinate - Will unlikely be exact coordinate of moved window (see above)
     * @param y Y coordinate - Will unlikely be exact coordinate of moved window (see above)
     * @param timeLimit Maximum amount of time to wait for the sync
     * @param sleepTime Time to sleep between each sync check
     * @throws XDoException If timeLimit is reached, XDoException is thrown
     */
    public void xdoMoveWindowSync(final xdo_t xdo, X11.Window window, int x, int y, int timeLimit,
                                  int sleepTime) throws XDoException {

        if (timeLimit <= 0) {
            throw new IllegalArgumentException ("timeLimit must be above 0!");
        }

        if (sleepTime <= 0) {
            throw new IllegalArgumentException("sleepTime must be above 0!");
        }

        //Getting original window location
        IntByReference originalX = new IntByReference();
        IntByReference originalY = new IntByReference();
        lib.xdo_get_window_location(xdo, window, originalX, originalY, null);

        IntByReference newX = new IntByReference();
        IntByReference newY = new IntByReference();
        long startTime = System.currentTimeMillis();
        lib.xdo_move_window(xdo, window, x, y);
        do {

            lib.xdo_get_window_location(xdo, window, newX, newY, null);
            if (System.currentTimeMillis() - startTime > timeLimit)  {
                throw new XDoException("Failed to move & sync window");
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                return;
            }

        } while (Math.abs(newX.getValue() - x) < maxXPad && Math.abs(newY.getValue() - y) < maxYPad);
    }

    public void xdoMoveWindowSync(final xdo_t xdo, X11.Window window, int x, int y) throws XDoException {
        xdoMoveWindowSync(xdo, window, x, y, 5000, 100);
    }

    public Pair<Integer, Integer> calculateWindowPadding(final xdo_t xdo, X11.Screen screen, X11.Window window) {
        X11 x11lib = Native.load( "X11", X11.class);
    }

    /**
     * Calculates the padding imposed by the X window manager.
     * IMPORTANT: THIS WILL MOVE THE WINDOW TO CALCULATE THE PADDING.
     * This may not be necessary if access to the X11 Library is available.
     * @param xdo
     * @param window
     * @param x
     * @param y
     * @return
     */
    /*
    public Pair<Integer, Integer> calculateWindowPadding(final xdo_t xdo, X11.Window window, int x, int y) {

    }*/

}
