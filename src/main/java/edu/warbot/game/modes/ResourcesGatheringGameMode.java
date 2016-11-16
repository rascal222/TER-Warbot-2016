package edu.warbot.game.modes;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarResource;
import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.agents.WarTurret;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.exceptions.UnauthorizedAgentException;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.ResourcesGatheringEndCondition;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.WarCircle;

public class ResourcesGatheringGameMode extends WarGame {
	
	private static final int NB_POSITION_GENERATIONS_MAX_PER_TURRET = 500;
	private static final int NB_POSITION_GENERATIONS_MAX_PER_FOOD = 500;
	
	private int resourcesNeededToWin;
	private WarCircle selectedPosition;

	public ResourcesGatheringGameMode(WarGameSettings settings, Object[] args) {
		super(settings);
		//this.resourcesNeededToWin = (Integer) args[0];
		this.resourcesNeededToWin = 10;
		this.selectedPosition = getMap().getTeamsPositions().get(0).get(new Random().nextInt(getMap().getTeamsPositions().get(0).size()));
		this.setEndCondition(new ResourcesGatheringEndCondition(this, resourcesNeededToWin));
	}

	@Override
	protected void doAfterEachTickForThisGameMode() {
		if (this.getMotherNatureTeam().getResources().size() == 0) {
			WarResource resource = null;
			try {
				resource = getMotherNatureTeam().instantiateNewWarResource(WarAgentType.WarFood.toString());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				System.err.println("Erreur lors de l'instanciation de la première ressource");
				e.printStackTrace();
			}
	        scheduler.launchAgent(resource);
	        
	        boolean validPosition = false; 
			int nbPositionsGenerated = 0;
			CartesianCoordinates pos = null;
			while (!validPosition && nbPositionsGenerated < NB_POSITION_GENERATIONS_MAX_PER_FOOD) {
				pos = CartesianCoordinates.getRandomInBounds(0, 0, (int) getMap().getWidth(), (int) getMap().getHeight());
				
				validPosition = true;
				// Distance a la zone de départ
				if (pos.distance(selectedPosition.getCenterPosition()) < selectedPosition.getRadius() + WarBase.DISTANCE_OF_VIEW) {
					validPosition = false;
				} else {
					// Distance à chacune des autres tourelles
					int i = 0;
					while (validPosition && i < getMotherNatureTeam().getAllAgents().size()) {
						WarAgent agent = getMotherNatureTeam().getAllAgents().get(i);
						if (agent instanceof WarTurret) {
							if (pos.distance(agent.getPosition()) < WarTurret.DISTANCE_OF_VIEW) {
								validPosition = false;
							}
						}
						i++;
					}
				}					
			}		
	        
	        resource.setPosition(pos);
	        resource.moveOutOfCollision();
		}
	}

	@Override
	protected void launchAllAgentsForThisGameMode() {
		// Choix de la zone de départ du joueur.
		InGameTeam playerInGameTeam = this.getPlayerTeams().get(0);
		AbstractWarMap map = this.getMap();
		
		// Placement de la base
		try {
            WarAgent base = playerInGameTeam.instantiateNewControllableWarAgent(WarAgentType.WarBase.toString());
            playerInGameTeam.getTeam().associateBrain((ControllableWarAgent) base);
            launcher.launchAgent(base);
            base.setRandomPositionInCircle(selectedPosition);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			System.err.println("Erreur lors de l'instanciation de la base");
			e.printStackTrace();
		} catch (UnauthorizedAgentException e) {
			System.err.println(e.getMessage());
		}
		
		// Placement des explorers
		int nbExplorers = this.getSettings().getNbAgentOfType(WarAgentType.WarExplorer);
		for (int countExplorer = 0; countExplorer < nbExplorers; countExplorer++) {
			try {
				WarAgent explorer = playerInGameTeam.instantiateNewControllableWarAgent(WarAgentType.WarExplorer.toString());
				playerInGameTeam.getTeam().associateBrain((ControllableWarAgent) explorer);
				launcher.launchAgent(explorer);
				explorer.setRandomPositionInCircle(selectedPosition);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				System.err.println("Erreur lors de l'instanciation de l'explorer n°" + countExplorer);
				e.printStackTrace();
			} catch (UnauthorizedAgentException e) {
				System.err.println(e.getMessage());
			}
		}		
		
		// Place les tourelles neutres à distance l'une de l'autre
		InGameTeam neutralTeam = this.getPlayerTeams().get(1);
		int nbTurrets = this.getSettings().getNbAgentOfType(WarAgentType.WarTurret);
		for (int countTurret = 0; countTurret < nbTurrets; countTurret++) {
			try {
				WarAgent turret = neutralTeam.instantiateNewControllableWarAgent(WarAgentType.WarTurret.toString());
				neutralTeam.getTeam().associateBrain((ControllableWarAgent) turret);
				launcher.launchAgent(turret);
				
				// Génère une position aléatoire pour la tourelle, a distance des autres tourelles et de la zone de départ sélectionnée.
				boolean validPosition = false; 
				int nbPositionsGenerated = 0;
				CartesianCoordinates pos = null;
				while (!validPosition && nbPositionsGenerated < NB_POSITION_GENERATIONS_MAX_PER_TURRET) {
					pos = CartesianCoordinates.getRandomInBounds(0, 0, (int) map.getWidth(), (int) map.getHeight());
					
					validPosition = true;
					// Distance a la zone de départ	
					if (pos.distance(selectedPosition.getCenterPosition()) < selectedPosition.getRadius() + WarTurret.DISTANCE_OF_VIEW) {
						validPosition = false;
					} else {
						// Distance à chacune des autres tourelles
						int i = 0;
						while (validPosition && i < neutralTeam.getAllAgents().size()) {
							WarAgent agent = neutralTeam.getAllAgents().get(i);
							if (agent instanceof WarTurret) {
								if (pos.distance(agent.getPosition()) < WarTurret.DISTANCE_OF_VIEW * 2) {
									validPosition = false;
								}
							}
							i++;
						}
					}	
				}		
				if (validPosition) {
					turret.setPosition(pos);
				} else {
					turret.setPosition(0, 0);
					turret.getLogger().severe("Pas de place pour la tourelle n°" + countTurret);
				}
				turret.moveOutOfCollision();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException 
					| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
				System.err.println("Erreur lors de l'instanciation de la tourelle n°" + countTurret);
				e.printStackTrace();
			} catch (UnauthorizedAgentException e) {
				System.err.println(e.getMessage());
			}
		}		
		
		// Place la première food.
		WarResource ressource = null;
		try {
			ressource = getMotherNatureTeam().instantiateNewWarResource(WarAgentType.WarFood.toString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			System.err.println("Erreur lors de l'instanciation de la première ressource");
			e.printStackTrace();
		}
        launcher.launchAgent(ressource);
        
        boolean validPosition = false; 
		int nbPositionsGenerated = 0;
		CartesianCoordinates pos = null;
		while (!validPosition && nbPositionsGenerated < NB_POSITION_GENERATIONS_MAX_PER_FOOD) {
			pos = CartesianCoordinates.getRandomInBounds(0, 0, (int) getMap().getWidth(), (int) getMap().getHeight());
			
			validPosition = true;
			// Distance a la zone de départ
			if (pos.distance(selectedPosition.getCenterPosition()) < selectedPosition.getRadius() + WarBase.DISTANCE_OF_VIEW) {
				validPosition = false;
			} else {
				// Distance à chacune des autres tourelles
				int i = 0;
				while (validPosition && i < getMotherNatureTeam().getAllAgents().size()) {
					WarAgent agent = getMotherNatureTeam().getAllAgents().get(i);
					if (agent instanceof WarTurret) {
						if (pos.distance(agent.getPosition()) < WarTurret.DISTANCE_OF_VIEW) {
							validPosition = false;
						}
					}
					i++;
				}
			}					
		}
        
        ressource.setPosition(pos);
        ressource.moveOutOfCollision();
	}

	@Override
	public boolean authorizedAgent(InGameTeam inGameTeam, WarAgentType agentType) {
		//System.out.println("IGT="+inGameTeam+"\t AT="+agentType+"\t isPlayer="+inGameTeam.getTeam().isPlayer()+"\t isIA="+inGameTeam.getTeam().isIA());
		//return true;
		if (inGameTeam.getTeam().isPlayer()) {
			if (agentType == WarAgentType.WarExplorer || 
				agentType == WarAgentType.WarBase) {
				return true;
			} 
		} 
		if (inGameTeam.getTeam().isIA()) {
			if (agentType == WarAgentType.WarTurret)
				return true;
		} 
		return false;
	}
}
