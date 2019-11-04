package pl.sdacademy.todolist.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static boolean validateData(String pattern, String data) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(data);
        return m.matches();
    }
}
