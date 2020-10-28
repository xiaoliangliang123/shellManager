package com.shell.manager.config;



import javax.swing.*;
import java.awt.*;


public class DataUtil {



    public static String getImgFromResourcesByName(String name) {
       return DataUtil.class.getResource("/imgs/"+name).getFile();
    }

    public static Cursor getCoursorFromResourcesByName(String name) {
        Image image = new ImageIcon(getImgFromResourcesByName(name)).getImage();
        Cursor coursor = Toolkit.getDefaultToolkit().createCustomCursor(image,new Point(10,20), "stick");
        return coursor;
    }

}
