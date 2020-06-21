import com.kingpulse.xdo_t;
import com.kingpulse.xdotool;
import com.sun.jna.Native;
import com.sun.jna.platform.unix.X11;
import org.junit.Test;

/**
 * JUnit tests for xdotool
 * Author: Edwin Heerschap
 */
public class LoadLib {

    public xdotool lib = (xdotool) Native.load("xdo", xdotool.class);


    @Test
    public void xdoload() {
        System.out.println(lib.xdo_version());
    }

    @Test
    public void xdo_new_test() {
        xdo_t xdo = lib.xdo_new(null); //TODO switch to gradle setting. null uses enviornment DISPLAY var.
    }



}
