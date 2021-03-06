package edu.warbot.gui.viewer.debug.eventlisteners;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.exceptions.UnauthorizedAgentException;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.WarGameListener;
import edu.warbot.gui.viewer.debug.DebugModePanel;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import javax.swing.*;

import madkit.action.SchedulingAction;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AddToolMouseListener implements MouseListener, MouseMotionListener, WarGameListener {

    private DebugModePanel _debugToolBar;
    private DebugToolsPnl _toolsPnl;

    private WarAgent currentCreatedAgent;

    private WarGame game;
    
    private SchedulingAction gameState;

    public AddToolMouseListener(DebugModePanel debugToolBar, DebugToolsPnl toolsPnl) {
        _debugToolBar = debugToolBar;
        _toolsPnl = toolsPnl;

        game = _debugToolBar.getViewer().getGame();
        
        WarGame.addWarGameListener(this);
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
            SchedulingAction stateBeforeAdd = gameState;
            _debugToolBar.getViewer().setMapExplorationEventsEnabled(false);
            if (_toolsPnl.getSelectedWarAgentTypeToCreate() != null) {
                CartesianCoordinates mouseClickPosition = _debugToolBar.getViewer().convertClickPositionToMapPosition(e.getX(), e.getY());
                game.setGamePaused();
                try {
                    if (_toolsPnl.getSelectedWarAgentTypeToCreate().getCategory() == WarAgentCategory.Resource) {
                        currentCreatedAgent = game.getMotherNatureTeam().instantiateNewWarResource(_toolsPnl.getSelectedWarAgentTypeToCreate().toString());
                        _debugToolBar.getViewer().launchAgent(currentCreatedAgent);
                        currentCreatedAgent.setPosition(mouseClickPosition.getX(), mouseClickPosition.getY());
                        currentCreatedAgent.moveOutOfCollision();
                    } else {
                        if (_toolsPnl.getSelectedInGameTeamForNextCreatedAgent() != null) {
                            if (_toolsPnl.getSelectedWarAgentTypeToCreate().isControllable()) {
                                currentCreatedAgent = _toolsPnl.getSelectedInGameTeamForNextCreatedAgent().instantiateNewControllableWarAgent(_toolsPnl.getSelectedWarAgentTypeToCreate().toString());
                                _toolsPnl.getSelectedInGameTeamForNextCreatedAgent().getTeam().associateBrain((ControllableWarAgent) currentCreatedAgent);
                            } else {
                                currentCreatedAgent = _toolsPnl.getSelectedInGameTeamForNextCreatedAgent().instantiateNewBuilding(_toolsPnl.getSelectedWarAgentTypeToCreate().toString());
                                ((AliveWarAgent) currentCreatedAgent).init(((AliveWarAgent) currentCreatedAgent).getMaxHealth());
                            }
                            _debugToolBar.getViewer().launchAgent(currentCreatedAgent);
                            currentCreatedAgent.setPosition(mouseClickPosition.getX(), mouseClickPosition.getY());
                            currentCreatedAgent.moveOutOfCollision();
                        } else {
                            JOptionPane.showMessageDialog(_debugToolBar, "Veuillez sélectionner une équipe pour cet agent.", "Création d'un agent impossible", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    _debugToolBar.getViewer().getFrame().repaint();
                } catch (UnauthorizedAgentException ex) {
	    			System.err.println(ex.getMessage());
	    			JOptionPane.showMessageDialog(_debugToolBar, ex.getMessage());
                } catch (Exception ex) { // TODO exception la plus précise possible
                    System.err.println("Erreur lors de l'instanciation de l'agent " + _toolsPnl.getSelectedWarAgentTypeToCreate().toString());
                    ex.printStackTrace();
                }
                if(stateBeforeAdd == SchedulingAction.RUN)
	                game.setGameResumed();
            } else {
                JOptionPane.showMessageDialog(_debugToolBar, "Veuillez sélectionner un type d'agent.", "Création d'un agent impossible", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            currentCreatedAgent = null;
            _debugToolBar.getViewer().setMapExplorationEventsEnabled(true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentCreatedAgent != null) {
            CartesianCoordinates mousePosition = _debugToolBar.getViewer().convertClickPositionToMapPosition(e.getX(), e.getY());
            PolarCoordinates movement = new CartesianCoordinates(mousePosition.getX() - currentCreatedAgent.getX(), mousePosition.getY() - currentCreatedAgent.getY()).toPolar();
            currentCreatedAgent.setHeading(movement.getAngle());
            if (currentCreatedAgent instanceof ControllableWarAgent)
                ((ControllableWarAgent) currentCreatedAgent).forcePerceptsUpdate();
            _debugToolBar.getViewer().getFrame().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

	@Override
	public void onNewTeamAdded(InGameTeam newInGameTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTeamLost(InGameTeam removedInGameTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGamePaused() {
		// TODO Auto-generated method stub
		gameState = SchedulingAction.PAUSE;
	}

	@Override
	public void onGameResumed() {
		// TODO Auto-generated method stub
		gameState = SchedulingAction.RUN;
	}
}