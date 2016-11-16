package edu.warbot.gui.viewer.debug.eventlisteners;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.game.WarGame;
import edu.warbot.gui.viewer.debug.DebugModePanel;
import edu.warbot.tools.geometry.CartesianCoordinates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MoveToolMouseListener implements MouseListener, MouseMotionListener {

    private DebugModePanel _debugToolBar;
    private WarAgent _currentSelectedAgent;
    private WarGame game;

    public MoveToolMouseListener(DebugModePanel debugToolBar) {
        _debugToolBar = debugToolBar;
        _currentSelectedAgent = null;
        game = _debugToolBar.getViewer().getGame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            _debugToolBar.getViewer().setMapExplorationEventsEnabled(false);

            // On sélectionne l'agent sous le clique de souris
            CartesianCoordinates mouseClickPosition = _debugToolBar.getViewer().convertClickPositionToMapPosition(e.getX(), e.getY());
            ArrayList<WarAgent> agents = game.getAllAgentsInRadius(mouseClickPosition.getX(), mouseClickPosition.getY(), 3);
            if (agents.size() > 0) {
                _currentSelectedAgent = agents.get(0);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            _currentSelectedAgent = null;
            _debugToolBar.getViewer().setMapExplorationEventsEnabled(true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (_currentSelectedAgent != null) {
            CartesianCoordinates newPos = _debugToolBar.getViewer().convertClickPositionToMapPosition(e.getX(), e.getY());
            newPos.normalize(0, game.getMap().getWidth() - 1, 0, game.getMap().getHeight() - 1);
            _currentSelectedAgent.setPosition(newPos);
            _currentSelectedAgent.moveOutOfCollision();
            if (_currentSelectedAgent instanceof ControllableWarAgent)
                ((ControllableWarAgent) _currentSelectedAgent).forcePerceptsUpdate();

            _debugToolBar.getViewer().getFrame().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
