package edu.warbot.brains.capacities;

import edu.warbot.launcher.WarGameConfig;

public interface Building
{
	public static final double MAX_DISTANCE_BUILD = WarGameConfig.getMaxDistanceBuild();
    public static final double REPAIRS_MULTIPLIER = WarGameConfig.getRepairsMultiplier();
    
    public int getRepairsAmountWithCost(int cost);
    public int getCostToRepair(int repairsAmout);
}
