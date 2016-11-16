package edu.warbot.brains.implementations;

import edu.warbot.agents.actions.constants.CreatorActions;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.capacities.Creator;

public class WarCreatorBrainImplementation extends WarBrainImplementation implements CreatorActions, Creator {

    @Override
    public WarAgentType getNextAgentToCreate() {
        return ((Creator) getAgent()).getNextAgentToCreate();
    }

    @Override
    public void setNextAgentToCreate(WarAgentType nextAgentToCreate) {
        ((Creator) getAgent()).setNextAgentToCreate(nextAgentToCreate);
    }

    @Override
    public boolean isAbleToCreate(WarAgentType agent) {
        return ((Creator) getAgent()).isAbleToCreate(agent);
    }
}
