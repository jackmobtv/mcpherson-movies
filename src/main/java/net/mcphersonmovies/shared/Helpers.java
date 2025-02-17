package net.mcphersonmovies.shared;

import java.util.Arrays;

public class Helpers {
    public static String CharToString(char[] array) {
        return Arrays.toString(array).replace("[", "").replace("]", "").replace(", ", "");
    }
}
