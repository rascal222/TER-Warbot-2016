package edu.warbot.gui.launcher.mapSelection;

import edu.warbot.game.WarGameSettings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSelectionListener implements ActionListener {

	private MapMiniature mapMiniature;
	private WarGameSettings warGameSettings;
    private MapSelectionDialog selectionDialog;

    public MapSelectionListener(MapMiniature mapMiniature, WarGameSettings warGameSettings) {
    	this.mapMiniature = mapMiniature;
    	this.warGameSettings = warGameSettings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectionDialog = new MapSelectionDialog(mapMiniature, warGameSettings);
        selectionDialog.setVisible(true);
    }
}
