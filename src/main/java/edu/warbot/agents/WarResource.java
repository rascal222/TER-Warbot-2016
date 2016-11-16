package edu.warbot.agents;

import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

public abstract class WarResource extends WarAgent {

    public static final double MAX_DISTANCE_TAKE = WarGameConfig.getMaxDistanceTake();
    private static final String ACTION_DEFAULT = "action";

    public WarResource(Hitbox hitbox, InGameTeam inGameTeam) {
        super(ACTION_DEFAULT, inGameTeam, hitbox);
    }

    @Override
    protected void activate() {
        super.activate();
        do {
            randomLocation();
        } while (isGoingToBeOutOfMap() || isGoingToBeOverAnOtherAgent());
    }

    @Override
    public void kill() {
        killAgent(this);
        getTeam().removeWarAgent(this);
    }

    public String action() {
        return ACTION_DEFAULT;
    }

}
