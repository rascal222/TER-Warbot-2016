package edu.warbot.agents;

import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.agents.actions.PickerActionsMethods;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.brains.capacities.Picker;
import edu.warbot.game.InGameTeam;

import java.util.ArrayList;
import java.util.logging.Level;

public abstract class MovableWarAgent extends ControllableWarAgent implements PickerActionsMethods, Picker, MovableActionsMethods, Movable {

    private double _speed;

    public MovableWarAgent(String firstActionToDo, InGameTeam inGameTeam, Hitbox hitbox, WarBrain brain, double distanceOfView, double angleOfView, int cost, int maxHealth, int bagSize, double speed, int armor) {
        super(firstActionToDo, inGameTeam, hitbox, brain, distanceOfView, angleOfView, cost, maxHealth, bagSize, armor);

        this._speed = speed;
    }

    public double getSpeed() {
        return _speed;
    }

    @Override
    public String move() {
        logger.log(Level.FINEST, this.toString() + " moving...");
        if (!isBlocked()) {
            logger.log(Level.FINER, this.toString() + " moved of " + getSpeed());
            fd(getSpeed());
        }
        return getBrain().action();
    }

    @Override
    public String take() {
        logger.log(Level.FINEST, this.toString() + " taking...");
        if (!isBagFull()) {
            ArrayList<WarResource> reachableResources = getTeam().getGame().getMotherNatureTeam().getAccessibleResourcesFor(this);
            if (reachableResources.size() > 0) {
//				killAgent(reachableResources.get(0));
                reachableResources.get(0).kill();
                addElementInBag();
                logger.log(Level.FINER, this.toString() + " take " + reachableResources.get(0).getClass().getSimpleName());
            }
        }
        return getBrain().action();
    }

    @Override
    public boolean isBlocked() {
        return isGoingToBeOutOfMap() || isGoingToBeOverAnOtherAgent();
    }
}
