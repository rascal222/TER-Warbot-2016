package edu.warbot.gui;

import edu.warbot.tools.WarIOTools;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GuiIconsLoader {

    private static final String ASSETS_DIR = "assets";
    private static final String LOGO_DIR = ASSETS_DIR + File.separatorChar + "logos";
    private static final String ICONS_DIR = ASSETS_DIR + File.separatorChar + "icons";

    public static ImageIcon getLogo(String fileName) {
        InputStream is = GuiIconsLoader.class.getClassLoader().getResourceAsStream(ASSETS_DIR + "/" + "logos" + "/" + fileName);
        if (is != null) {
            try {
                ImageIcon ii = new ImageIcon(WarIOTools.toByteArray(is));
                is.close();
                return ii;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ImageIcon(LOGO_DIR + File.separatorChar + fileName);
    }

    public static ImageIcon getIcon(String fileName) {
        InputStream is = GuiIconsLoader.class.getClassLoader().getResourceAsStream(ASSETS_DIR + "/" + "icons" + "/" + fileName);

        if (is != null) {
            try {
                ImageIcon ii = new ImageIcon(WarIOTools.toByteArray(is));
                is.close();
                return ii;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ImageIcon(ICONS_DIR + File.separatorChar + fileName);
    }

    public static ImageIcon getWarbotLogo() {
        return GuiIconsLoader.getLogo("logo.jpg");
    }

}
