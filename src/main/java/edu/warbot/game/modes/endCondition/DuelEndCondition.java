package edu.warbot.game.modes.endCondition;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.TeamListener;

public class DuelEndCondition extends AbstractEndCondition implements TeamListener {

    public DuelEndCondition(WarGame game) {
        super(game);
        for (InGameTeam t : getGame().getPlayerTeams()) {
            t.addTeamListener(this);
        }
    }

    @Override
    public void doAfterEachTick() {
//        for (InGameTeam t : getGame().getPlayerTeams()) {
//            if (t.getNbUnitsLeftOfType(WarAgentType.WarBase) == 0) {
//                getGame().setTeamAsLost(t);
//            }
//        }
    }

    @Override
    public boolean isGameEnded() {
//        int nbTeamsWithoutBase = 0;
//        for (InGameTeam t : getGame().getPlayerTeams()) {
//            if (t.getNbUnitsLeftOfType(WarAgentType.WarBase) == 0) {
//                nbTeamsWithoutBase++;
//            }
//        }
        return getGame().getPlayerTeams().size() <= 1;
    }

    @Override
    public void onAgentAdded(WarAgent newAgent) {

    }

    @Override
    public void onAgentRemoved(WarAgent removedAgent) {
        if (removedAgent.getTeam().getNbUnitsLeftOfType(WarAgentType.WarBase) == 0) {
            getGame().setTeamAsLost(removedAgent.getTeam());
        }
    }
}
