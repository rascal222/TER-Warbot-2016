package edu.warbot.brains.implementations;

import edu.warbot.agents.actions.constants.AgressiveActions;
import edu.warbot.brains.capacities.Agressive;

public class WarAgressiveBrainImplementation extends WarBrainImplementation implements AgressiveActions, Agressive {

    @Override
    public boolean isReloaded() {
        return ((Agressive) getAgent()).isReloaded();
    }

    @Override
    public boolean isReloading() {
        return ((Agressive) getAgent()).isReloading();
    }
}
