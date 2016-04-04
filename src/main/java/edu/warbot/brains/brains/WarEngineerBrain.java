package edu.warbot.brains.brains;

import edu.warbot.agents.actions.BuilderActionsMethods;
import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.agents.actions.PickerActionsMethods;
import edu.warbot.agents.actions.constants.BuilderActions;
import edu.warbot.agents.actions.constants.MovableActions;
import edu.warbot.agents.actions.constants.PickerActions;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Builder;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.brains.capacities.Picker;

public abstract class WarEngineerBrain extends WarBrain implements 
		BuilderActionsMethods, BuilderActions, Builder,
        MovableActionsMethods, MovableActions, Movable,
        PickerActionsMethods, PickerActions, Picker
{

    @Override
    public final String move() {
        return ACTION_MOVE;
    }

    @Override
    public final String build() {
        return ACTION_BUILD;
    }

    @Override
    public final String repair() {
        return ACTION_REPAIR;
    }
    
    @Override
    public final String take() {
        return ACTION_TAKE;
    }

}
