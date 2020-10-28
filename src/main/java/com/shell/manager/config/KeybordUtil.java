package com.shell.manager.config;

public class KeybordUtil {

    public static final String  KEY_ENTER = "Enter";
    public static final String  KEY_TAB = "Tab";

    public static boolean isEnter(String keycode) {
        return KEY_ENTER.equals(keycode);
    }


    public static boolean isTab(String keycode){
        return KEY_ENTER.equals(KEY_TAB);
    }

    public static boolean isLetterOrDigit(char keycode) {
        return Character.isLetterOrDigit(keycode);
    }
}
