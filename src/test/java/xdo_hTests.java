import com.kingpulse.structs.xdo_t;
import com.kingpulse.xdotool;
import com.sun.jna.ptr.IntByReference;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for functions defined in xdo.h
 * Author: Edwin Heerschap
 */
public class xdo_hTests {

    public xdotool lib = xdotool.loadLib();

    /*
    The following removes boiler plate code for try catch finally on an xdo_t instance.
     */
    private interface xdo_t_interface {

        void test(xdo_t xdo);

    }

    private void xdo_tEnsureFree(xdo_t xdo, xdo_t_interface testInt) {
        try {
            testInt.test(xdo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lib.xdo_free(xdo);
        }
    }

    @Test
    public void xdo_version_test() {
        String major = lib.xdo_version().substring(0, lib.xdo_version().indexOf('.'));
        Assert.assertEquals(major, TestSettings.xdoMajorVersion);
    }

    @Test
    public void xdo_new_test() {
        xdo_t xdo = lib.xdo_new(TestSettings.display); //TODO switch to gradle setting. null uses enviornment DISPLAY var.
        lib.xdo_free(xdo);
    }

    @Test
    public void xdo_move_mouse_test() {
        xdo_t xdo = lib.xdo_new(TestSettings.display);

        xdo_t_interface xdoTest = (x) -> {

            IntByReference xVal = new IntByReference();
            IntByReference yVal = new IntByReference();
            IntByReference screenNum = new IntByReference();

            lib.xdo_move_mouse(x, 0, 0, TestSettings.screen);
            lib.xdo_get_mouse_location(xdo, xVal, yVal, screenNum);
            Assert.assertEquals(xVal.getValue(), 0);
            Assert.assertEquals(yVal.getValue(), 0);
            Assert.assertEquals(screenNum.getValue(), TestSettings.screen);

            lib.xdo_move_mouse(xdo, 127, 127, TestSettings.screen);
            lib.xdo_get_mouse_location(xdo, xVal, yVal, screenNum);
            Assert.assertEquals(xVal.getValue(), 127);
            Assert.assertEquals(yVal.getValue(), 127);
            Assert.assertEquals(screenNum.getValue(), TestSettings.screen);

        };

        xdo_tEnsureFree(xdo, xdoTest);
    }

    @Test
    public void xdo_get_window_at_mouse() {
        xdo_t xdo = lib.xdo_new(TestSettings.display);
        ProcessBuilder pb = new ProcessBuilder("xclock");

    }


}
