package edu.warbot.game.modes.endCondition;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.MotherNatureTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.TeamListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NumberLostAgentEndCondition extends AbstractEndCondition implements TeamListener {

    private long maxAgentLost;

    private InGameTeam inGameTeamVictory;
    private List<InGameTeam> teamsOfGame;

    private Map<InGameTeam, Long> lostAgentByTeam;

    public NumberLostAgentEndCondition(WarGame game, long maxAgentLost) {
        super(game);
        this.maxAgentLost = maxAgentLost;
        this.inGameTeamVictory = null;
        this.teamsOfGame = game.getAllTeams();

        this.lostAgentByTeam = new HashMap<InGameTeam, Long>();

        for (InGameTeam inGameTeam : teamsOfGame)
            if (!(inGameTeam instanceof MotherNatureTeam))
                lostAgentByTeam.put(inGameTeam, new Long(0));

        for (InGameTeam t : getGame().getPlayerTeams()) {
            t.addTeamListener(this);
        }

    }

    @Override
    public void doAfterEachTick() {
        // VOID
    }

    @Override
    public boolean isGameEnded() {

        if (getInGameTeamVictory() != null) {
            System.out.println("InGameTeam victory : " + inGameTeamVictory.getName());
            return true;
        }

        return false;
    }

    @Override
    public void onAgentAdded(WarAgent newAgent) {
        // VOID
    }

    @Override
    public void onAgentRemoved(WarAgent removedAgent) {

        if (removedAgent instanceof ControllableWarAgent) {

            Iterator<Entry<InGameTeam, Long>> entries = lostAgentByTeam.entrySet().iterator();

            while (entries.hasNext()) {
                Entry<InGameTeam, Long> thisEntry = entries.next();

                Long newNumberLost = thisEntry.getValue() + 1;

                thisEntry.setValue(newNumberLost);

                if (thisEntry.getValue() > maxAgentLost)
                    whoTeamWin(removedAgent);
            }
        }

    }


    public void whoTeamWin(WarAgent removedAgent) {
        for (InGameTeam inGameTeam : teamsOfGame)
            if (!(inGameTeam instanceof MotherNatureTeam) && !(inGameTeam.equals(removedAgent.getTeam())))
                inGameTeamVictory = inGameTeam;
    }

    public InGameTeam getInGameTeamVictory() {
        return inGameTeamVictory;
    }


}