package team.redrock.XSSFilterTest;

import org.apache.sling.xss.XSSAPI;
import org.apache.sling.xss.impl.XSSAPIImpl;
import org.junit.Test;

public class XSSTest {
    @Test
    public void test() {
        XSSAPI xssapi = new XSSAPIImpl();
        String test = "<button onclick=\"alert(window.cookies); alert(window.cookie)'\">mdzz</button><iframe src='http://messiahhh.com/test.js'><img src='http://messiahhh.com/test.js'><script>alert(\"123\")</script>";
        System.out.println(xssapi.encodeForHTMLAttr(test));
    }
}
