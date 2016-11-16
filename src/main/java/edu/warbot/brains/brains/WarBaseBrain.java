package edu.warbot.brains.brains;

import edu.warbot.agents.actions.CreatorActionsMethods;
import edu.warbot.agents.actions.constants.CreatorActions;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Creator;

/**
 *
 */
public abstract class WarBaseBrain extends WarBrain implements CreatorActionsMethods, CreatorActions, Creator {

    @Override
    public String action() {
        return ACTION_IDLE;
    }

    @Override
    public final String create() {
        return ACTION_CREATE;
    }

}
