package edu.warbot.agents;

import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

public abstract class WarBuilding extends AliveWarAgent {

    public static final double MAX_DISTANCE_BUILD = WarGameConfig.getMaxDistanceBuild();
    public static final double REPAIRS_MULTIPLIER = WarGameConfig.getRepairsMultiplier();

    public WarBuilding(InGameTeam inGameTeam, Hitbox hitbox, int cost, int maxHealth, int armor) {
        super(ACTION_IDLE, inGameTeam, hitbox, cost, maxHealth, armor);
        init(getRepairsAmountWithCost(cost));
    }

    public static int getRepairsAmountWithCost(int cost) {
        return ((Double) (REPAIRS_MULTIPLIER * cost)).intValue();
    }

    public static int getCostToRepair(int repairsAmout) {
        return ((Double) (repairsAmout / REPAIRS_MULTIPLIER)).intValue();
    }

    @Override
    public String idle() {
        return ACTION_IDLE;
    }

}
