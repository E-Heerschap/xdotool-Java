package io.github.kingpulse;

import com.sun.jna.Native;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import io.github.kingpulse.structs.xdo_search_t;
import io.github.kingpulse.structs.xdo_t;
import lombok.Getter;
import lombok.Setter;

public class XFacade {

    private static xdotool lib;

    private static X11 x11lib;

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

    /**
     * Ensures attempted load of libxdo and libx11.
     * Uses "xdo" and "X11" for names.
     * xdotool will likely require libx11 installation
     * for a long time considering the low maintenance of
     * the repository. However, the libx11 dependency MAY be removed
     * in the future: https://github.com/jordansissel/xdotool/blob/master/Makefile#L145
     */
    public XFacade() {
        XFacade.lib = Native.load("xdo", xdotool.class);
        XFacade.x11lib = Native.load("X11", X11.class);
    }

    /**
     *
     * @param lib loaded xdotool library
     * @param x11lib loaded x11 library
     */
    public XFacade(xdotool lib, X11 x11lib) {
        XFacade.lib = lib;

        //If different lib, old one should be gc'ed.
        //https://stackoverflow.com/questions/41056322/how-to-dispose-library-loaded-with-jna
        XFacade.x11lib = x11lib;

    }

    private boolean isX11Loaded() {
        return XFacade.x11lib != null;
    }

    /**
     * Performs the xdo_search_windows function and performs required conversions.
     *
     * @param xdo    xdo_t instance
     * @param search search structure defining the search
     * @return Array of X11 windows matching the search
     */
    public X11.Window[] searchWindows(final xdo_t xdo, final xdo_search_t search) {

        PointerByReference windows = new PointerByReference();
        IntByReference numWindows = new IntByReference();

        lib.xdo_search_windows(xdo, search, windows, numWindows);
        X11.Window[] windowArr = new X11.Window[numWindows.getValue()];

        for (int i = 0; i < numWindows.getValue(); i++) {
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
    public void moveWindowSync(final xdo_t xdo, X11.Window window, int x, int y, int timeLimit,
                               int sleepTime) throws XDoException {

        if (timeLimit <= 0) {
            throw new IllegalArgumentException("timeLimit must be above 0!");
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
            if (System.currentTimeMillis() - startTime > timeLimit) {
                throw new XDoException("Failed to move & sync window");
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                return;
            }

        } while (Math.abs(newX.getValue() - x) < maxXPad && Math.abs(newY.getValue() - y) < maxYPad);

    }

    public void moveWindowSync(final xdo_t xdo, X11.Window window, int x, int y) throws XDoException {
        moveWindowSync(xdo, window, x, y, 5000, 100);
    }

    /**
     * This is fixes a bug in the current xdotool displaying the incorrect location.
     * Until that is fixed, this will work.
     *
     * @param xdo    xdo_t instance
     * @param window window to get location of
     */
    public void getWindowLocation(final xdo_t xdo, X11.Window window, IntByReference x, IntByReference y) {
        X11.XWindowAttributes winAttr = new X11.XWindowAttributes();
        x11lib.XGetWindowAttributes(xdo.xdpy, window, winAttr);
        X11.WindowByReference c = new X11.WindowByReference();
        X11.Window root = winAttr.root; //Getting relative to root coords. (0, 0) -> TOP LEFT

        x11lib.XTranslateCoordinates(xdo.xdpy, window, root, 0, 0, x, y, c);

        if (c.getPointer() != null) {
            x11lib.XFree(c.getPointer());
        }
    }

}
