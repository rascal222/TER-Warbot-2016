package edu.warbot.game.modes.endCondition;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;

public class RelicGatheringEndCondition extends AbstractEndCondition {

    private int resourcesNeededToWin;
    private InGameTeam winner;

    public RelicGatheringEndCondition(WarGame game, int resourcesNeededToWin) {
        super(game);
        this.resourcesNeededToWin = resourcesNeededToWin;
    }

    @Override
    public void doAfterEachTick() {
    	InGameTeam tIA = getGame().getPlayerTeams().get(1);
        InGameTeam tPlayer = getGame().getPlayerTeams().get(0);
        int nbExplorers = 0;
        int currentTeamResources = 0;
        for (ControllableWarAgent agent : tPlayer.getControllableAgents()) {
            if (agent instanceof WarBase) {
                currentTeamResources += agent.getNbElementsInBag();
            }
            if (agent instanceof WarExplorer) {
            	nbExplorers++;
            }
        }
        if (nbExplorers == 0) {
        	this.winner = tIA;
        }
        if (currentTeamResources >= resourcesNeededToWin) {
            this.winner = tPlayer;
        }
    }

    @Override
    public boolean isGameEnded() {
        if (winner != null) {
            for (InGameTeam t : getGame().getPlayerTeams()) {
                if (!t.equals(winner)) {
                    getGame().setTeamAsLost(t);
                }
            }
            return true;
        }
        return false;
    }
}
