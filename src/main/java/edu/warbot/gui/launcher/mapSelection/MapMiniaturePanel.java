package edu.warbot.gui.launcher.mapSelection;

import edu.warbot.maps.AbstractWarMap;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MapMiniaturePanel extends JPanel {

    private MapMiniature mapMiniature;
    private JLabel lblMapName;

    public MapMiniaturePanel(MapMiniature mapMiniature) {
        super(new BorderLayout());
        this.mapMiniature = mapMiniature;

        JPanel pnlMiniature = new JPanel(new FlowLayout());
        pnlMiniature.add(mapMiniature);
        add(pnlMiniature, BorderLayout.CENTER);

        lblMapName = new JLabel(mapMiniature.getMap().getName());
        lblMapName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10 + ((Double) (mapMiniature.getMiniatureSize() * 2.)).intValue()));
        lblMapName.setHorizontalAlignment(SwingConstants.CENTER);
        lblMapName.setVerticalAlignment(SwingConstants.TOP);
        lblMapName.setPreferredSize(new Dimension(lblMapName.getWidth(), 25));
        add(lblMapName, BorderLayout.SOUTH);
    }

    public void setMap(AbstractWarMap map) {
        mapMiniature.setMap(map);
        lblMapName.setText(mapMiniature.getMap().getName());
    }

}
