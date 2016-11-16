package edu.warbot.brains.brains;

import edu.warbot.agents.actions.AgressiveActionsMethods;
import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.agents.actions.PickerActionsMethods;
import edu.warbot.agents.actions.constants.AgressiveActions;
import edu.warbot.agents.actions.constants.MovableActions;
import edu.warbot.agents.actions.constants.PickerActions;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Agressive;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.brains.capacities.Picker;
import edu.warbot.brains.capacities.Targeter;

public abstract class WarRocketLauncherBrain extends WarBrain implements
		AgressiveActionsMethods, AgressiveActions, Agressive,
        MovableActionsMethods, MovableActions, Movable,
        PickerActionsMethods, PickerActions, Picker,
        Targeter
        {

    @Override
    public String action() {
        return ACTION_IDLE;
    }

    @Override
    public final String move() {
        return ACTION_MOVE;
    }

    @Override
    public final String fire() {
        return ACTION_FIRE;
    }

    @Override
    public final String beginReloadWeapon() {
        return ACTION_RELOAD;
    }
    
    @Override
    public final String take() {
        return ACTION_TAKE;
    }

}
