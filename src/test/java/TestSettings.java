/**
 * Stores test settings.
 * Probably a better way of doing this exists... But this will do for now.
 * Author: Edwin Heerschap
 */
public class TestSettings {

    /*
    Thought: Maybe use Xvfb to make virtual display buffer and test with that?
     */

    public static String display = ":1";

    public static int screen = 0;

    public static String xdoMajorVersion = "3";

    public static String windowProcess = "xclock";
}
