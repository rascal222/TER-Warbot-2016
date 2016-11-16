package edu.warbot.gui.viewer.debug.eventlisteners;

import edu.warbot.agents.WarAgent;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;
import edu.warbot.tools.geometry.CartesianCoordinates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class InfosToolMouseListener implements MouseListener {

    private DebugToolsPnl debugToolsPnl;

    public InfosToolMouseListener(DebugToolsPnl debugToolsPnl) {
        this.debugToolsPnl = debugToolsPnl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // On sélectionne l'agent sous le clique de souris
            CartesianCoordinates mouseClickPosition = debugToolsPnl.getDebugToolBar().getViewer().convertClickPositionToMapPosition(e.getX(), e.getY());
            ArrayList<WarAgent> agents = debugToolsPnl.getDebugToolBar().getViewer().getGame().getAllAgentsInRadius(mouseClickPosition.getX(), mouseClickPosition.getY(), 3);
            if (agents.size() > 0) {
                debugToolsPnl.setSelectedAgent(agents.get(0));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
