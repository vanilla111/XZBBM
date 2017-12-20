package team.redrock.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
/**
 * Created by wang on 2017/9/22.
 */
public class PropertiesUtil {

    private static Properties properties;

    static {
        String fileName = "wxConfig.properties";
        properties = new Properties();
        try {
            properties.load(
                    new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key.trim());
        if ("".equals(value))
            return null;

        return value.trim();
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key.trim());
        if ("".equals(value))
            return defaultValue;

        return value.trim();
    }
}
