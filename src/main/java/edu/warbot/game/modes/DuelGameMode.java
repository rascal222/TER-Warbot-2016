package edu.warbot.game.modes;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.MotherNatureTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.DuelEndCondition;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.tools.geometry.WarCircle;

public class DuelGameMode extends WarGame {

    public DuelGameMode(WarGameSettings settings, Object[] args) {
        super(settings);
        this.setEndCondition(new DuelEndCondition(this));
    }

	@Override
	protected void launchAllAgentsForThisGameMode() {
		ArrayList<InGameTeam> playerInGameTeams = this.getPlayerTeams();
		AbstractWarMap map = this.getMap();
		ArrayList<ArrayList<WarCircle>> teamsPositions = map.getTeamsPositions();
		int teamCount = 0;
		MotherNatureTeam motherNatureTeam = this.getMotherNatureTeam();
		
		try {
		    int compteur;
		    for (InGameTeam t : playerInGameTeams) {
		        // On sélectionne aléatoirement la position de l'équipe depuis les différentes possibilités
			    WarCircle selectedPosition = teamsPositions.get(teamCount).get(new Random().nextInt(teamsPositions.get(teamCount).size()));
			    for (WarAgentType agentType : WarAgentType.values()) {
			    	for (compteur = 0; compteur < this.getSettings().getNbAgentOfType(agentType); compteur++) {
			    		try {
			                WarAgent agent = t.instantiateNewControllableWarAgent(agentType.toString());
			                t.getTeam().associateBrain((ControllableWarAgent) agent);
			                launcher.launchAgent(agent);
			                agent.setRandomPositionInCircle(selectedPosition);
			    		} catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
			    			System.err.println("Erreur lors de l'instanciation de l'agent. Type non reconnu : " + agentType);
			    			e.printStackTrace();
			    		}
		            // On créé autant de WarFood que d'agent au départ
		            motherNatureTeam.createAndLaunchResource(this.getMap(), launcher, WarAgentType.WarFood);
		            }
		        }
		        teamCount++;
		    }
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
		    System.err.println("Erreur lors de l'instanciation des classes à partir des données XML");
		    e.printStackTrace();
		}
	}
	
	@Override
	protected void doAfterEachTickForThisGameMode() {
		if (scheduler.getGVT() % this.getSettings().getFoodAppearanceRate() == 0) {
			this.getMotherNatureTeam().createAndLaunchResource(this.getMap(), scheduler, WarAgentType.WarFood);
        }
	}

}
