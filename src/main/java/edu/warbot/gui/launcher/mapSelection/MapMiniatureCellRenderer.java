package edu.warbot.gui.launcher.mapSelection;

import javax.swing.*;
import java.awt.*;

public class MapMiniatureCellRenderer implements ListCellRenderer<MapMiniature> {

    public MapMiniatureCellRenderer() {
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends MapMiniature> list, MapMiniature value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 2));
        } else {
            value.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        return new MapMiniaturePanel(value);
    }
}
