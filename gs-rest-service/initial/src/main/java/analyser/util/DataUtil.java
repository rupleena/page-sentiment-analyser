package analyser.util;

import org.springframework.util.StringUtils;

public final class DataUtil {

    public static final String UNKNOWN = "unknown";

    private DataUtil() {
    }


    public static String getString(String val) {
        return StringUtils.isEmpty(val) ? UNKNOWN : val;
    }
}
