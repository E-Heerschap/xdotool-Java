import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import io.github.kingpulse.XDoException;
import io.github.kingpulse.XFacade;
import io.github.kingpulse.structs.xdo_search_t;
import io.github.kingpulse.structs.xdo_t;
import io.github.kingpulse.xdotool;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * JUnit tests for functions defined in xdo.h
 * Author: Edwin Heerschap
 */
public class xdo_hTests {

    private static xdotool lib = xdotool.loadLib();

    private static Process proc = null;

    private static xdo_t xdo = null;

    @BeforeClass
    public static void init() {
        ProcessBuilder pb = new ProcessBuilder("xclock");
        try {
            proc = pb.start();
            xdo = lib.xdo_new(TestSettings.display);
            Thread.sleep(1000);
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void cleanup() {
        if (proc != null) {
            proc.destroyForcibly();
        }

        if (xdo != null) {
            lib.xdo_free(xdo);
        }
    }

    private X11.Window win = null;

    public X11.Window getXClockWindow() {
        if (win == null) {
            xdo_search_t search = new xdo_search_t();
            search.pid = (int) proc.pid();
            search.searchmask = (int) xdo_search_t.searchmask_SEARCH_PID;
            search.max_depth = -1;
            search.require = xdo_search_t.require_SEARCH_ANY;

            XFacade fac = new XFacade();
            X11.Window[] windows = fac.xdoSearchWindows(xdo, search);

            win = windows[0];
            return win;
        } else {
            return win;
        }
    }

    @Test
    public void xdo_version_test() {
        String major = lib.xdo_version().substring(0, lib.xdo_version().indexOf('.'));
        Assert.assertEquals(major, TestSettings.xdoMajorVersion);
    }

    @Test
    public void xdo_new_test() {
        xdo_t xdo_new = lib.xdo_new(TestSettings.display); //TODO switch to gradle setting. null uses enviornment DISPLAY var.
        lib.xdo_free(xdo_new);
    }

    @Test
    public void xdo_move_mouse_test() {

        IntByReference xVal = new IntByReference();
        IntByReference yVal = new IntByReference();
        IntByReference screenNum = new IntByReference();

        lib.xdo_move_mouse(xdo, 0, 0, TestSettings.screen);
        lib.xdo_get_mouse_location(xdo, xVal, yVal, screenNum);
        Assert.assertEquals(xVal.getValue(), 0);
        Assert.assertEquals(yVal.getValue(), 0);
        Assert.assertEquals(screenNum.getValue(), TestSettings.screen);

        lib.xdo_move_mouse(xdo, 127, 127, TestSettings.screen);
        lib.xdo_get_mouse_location(xdo, xVal, yVal, screenNum);
        Assert.assertEquals(xVal.getValue(), 127);
        Assert.assertEquals(yVal.getValue(), 127);
        Assert.assertEquals(screenNum.getValue(), TestSettings.screen);
    }

    @Test
    public void xdo_pid_search_test() {

        xdo_search_t search = new xdo_search_t();
        search.pid = (int) proc.pid();
        search.searchmask = (int) xdo_search_t.searchmask_SEARCH_PID;
        search.max_depth = -1;
        search.require = xdo_search_t.require_SEARCH_ANY;

        XFacade fac = new XFacade();
        X11.Window[] windows = fac.xdoSearchWindows(xdo, search);

        Assert.assertTrue(windows.length >= 1);
        X11.Window win = windows[0];

        PointerByReference name = new PointerByReference();
        IntByReference len = new IntByReference();
        IntByReference type = new IntByReference();

        lib.xdo_get_window_name(xdo, win, name, len, type);
        Assert.assertEquals(name.getValue().getString(0), TestSettings.windowProcess);

    }

    private void mv_window_test(int shiftAmount) {
        try {

            XFacade xdf = new XFacade();

            //Now shift and compare new position - window padding to the shift amount
            xdf.xdoMoveWindowSync(xdo, getXClockWindow(), shiftAmount, shiftAmount);
            IntByReference x = new IntByReference();
            IntByReference y = new IntByReference();

            //Using our func. Theirs contains bug.
            xdf.xdo_get_window_location(xdo, getXClockWindow(), x, y);

            //Tolerance values added as the Window managers decides final
            //position and usually distorts slightly for borders etc.
            Assert.assertTrue(10 > Math.abs(x.getValue() - shiftAmount));
            Assert.assertTrue(50 > Math.abs(y.getValue() - shiftAmount));

        } catch (XDoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void xdo_move_window_test() throws XDoException {

        XFacade xdf = new XFacade();

        xdf.xdoMoveWindowSync(xdo, getXClockWindow(), 200, 200);

        mv_window_test(200);
        mv_window_test(300);
        mv_window_test(600);

    }


}
