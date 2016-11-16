package edu.warbot.brains.implementations;

import edu.warbot.agents.actions.constants.MovableActions;
import edu.warbot.brains.capacities.Movable;

public class WarMovableBrainImplementation extends WarBrainImplementation implements MovableActions, Movable {

    @Override
    public boolean isBlocked() {
        return ((Movable) getAgent()).isBlocked();
    }

    @Override
    public double getSpeed() {
        return ((Movable) getAgent()).getSpeed();
    }
}
