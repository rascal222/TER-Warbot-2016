package edu.warbot.brains.brains;

import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.agents.actions.PickerActionsMethods;
import edu.warbot.agents.actions.constants.MovableActions;
import edu.warbot.agents.actions.constants.PickerActions;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.brains.capacities.Picker;

public abstract class WarExplorerBrain extends WarBrain implements 
		MovableActionsMethods, MovableActions, Movable,
        PickerActionsMethods, PickerActions, Picker
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
    public final String take() {
        return ACTION_TAKE;
    }
}
