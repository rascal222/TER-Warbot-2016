package edu.warbot.brains.capacities;

import edu.warbot.agents.enums.WarAgentType;

public interface Creator {

    WarAgentType getNextAgentToCreate();

    void setNextAgentToCreate(WarAgentType nextAgentToCreate);

    boolean isAbleToCreate(WarAgentType agent);

}
