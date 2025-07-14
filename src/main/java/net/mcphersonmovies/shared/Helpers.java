package net.mcphersonmovies.shared;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Helpers {
    public static String CharToString(char[] array) {
        return Arrays.toString(array).replace("[", "").replace("]", "").replace(", ", "");
    }

    public static String round(double number, int numDecPlaces) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(numDecPlaces, RoundingMode.HALF_UP).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    // https://stackoverflow.com/a/3149645/6629315
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    //Deep AI
    public static boolean isMobile(HttpServletRequest req) {
        String userAgentString = req.getHeader("User-Agent");

        // Parse the User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

        // Check if the user agent is a mobile device
        return userAgent.getOperatingSystem().getDeviceType() == DeviceType.MOBILE;
    }
}
