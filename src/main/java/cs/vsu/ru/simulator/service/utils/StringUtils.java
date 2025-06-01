package cs.vsu.ru.simulator.service.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {
    private static final String START_STRING_PATTERN = "(--start\\s+)\\d+";
    private static final Pattern START_PATTERN = Pattern.compile(START_STRING_PATTERN);

    public static String replaceStart(String message, double start) {
        var matcher = START_PATTERN.matcher(message);
        return matcher.replaceAll("$1" + start);
    }

}
