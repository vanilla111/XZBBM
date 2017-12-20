package team.redrock.util;

public class BBMUtil {

    public static int stringToInteger(String value, int defaultValue) {
        int temp;
        try {
            if (value == null) temp = defaultValue;
            else {
                temp = Integer.valueOf(value);
            }
        } catch (NumberFormatException e) {
            temp = defaultValue;
        }

        return temp;
    }
}
