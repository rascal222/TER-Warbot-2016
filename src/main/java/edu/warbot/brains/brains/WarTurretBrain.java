package edu.warbot.brains.brains;

import edu.warbot.agents.actions.AgressiveActionsMethods;
import edu.warbot.agents.actions.constants.AgressiveActions;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Agressive;

public abstract class WarTurretBrain extends WarBrain implements AgressiveActionsMethods, AgressiveActions, Agressive {

    @Override
    public String action() {
        return ACTION_IDLE;
    }

    @Override
    public final String fire() {
        return ACTION_FIRE;
    }

    @Override
    public final String beginReloadWeapon() {
        return ACTION_RELOAD;
    }

}
