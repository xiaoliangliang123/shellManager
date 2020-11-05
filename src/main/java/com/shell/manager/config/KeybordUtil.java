package com.shell.manager.config;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class KeybordUtil {

    public static final String  KEY_ENTER = "Enter";
    public static final String  KEY_TAB = "Tab";
    public static final char  KEY_TAB_CMD = '\t';
    public static final char  KEY_ENTER_CMD = '\n';


    public static boolean isEnter(String keycode) {
        return KEY_ENTER.equals(keycode);
    }


    public static boolean isTab(String keycode){
        return KEY_TAB.equals(keycode);
    }

    public static boolean isLetterOrDigit(char keycode) {
        return Character.isLetterOrDigit(keycode);
    }

    public static boolean isTabChar(char keyChar) {
        return Character.compare(KEY_TAB_CMD,keyChar)==0;
    }

    public static boolean isEnterChar(char keyChar) {
        return Character.compare(KEY_ENTER_CMD,keyChar)==0;
    }
}
