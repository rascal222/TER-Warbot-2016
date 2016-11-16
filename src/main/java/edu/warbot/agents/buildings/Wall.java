package edu.warbot.agents.buildings;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.capacities.Building;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;

public class Wall extends AliveWarAgent implements Building
{
    public static final int COST;
    public static final int MAX_HEALTH;
    public static final int ARMOR;

    static
    {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.Wall);
        COST = (int) data.get(WarGameConfig.AGENT_CONFIG_COST);
        MAX_HEALTH = (int) data.get(WarGameConfig.AGENT_CONFIG_MAX_HEALTH);
        ARMOR = (int) data.get(WarGameConfig.AGENT_CONFIG_ARMOR);
    }

    public Wall(InGameTeam inGameTeam)
    {
        super(ACTION_IDLE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.Wall), COST, MAX_HEALTH, ARMOR);
    }

    public WarAgentType getType()
    {
        return WarAgentType.Wall;
    }

	@Override
	public String idle()
	{
		return ACTION_IDLE;
	}

	@Override
	public int getRepairsAmountWithCost(int cost)
	{
		return ((Double) (REPAIRS_MULTIPLIER * cost)).intValue();
		
	}

	@Override
	public int getCostToRepair(int repairsAmout)
	{
		return ((Double) (repairsAmout / REPAIRS_MULTIPLIER)).intValue();
	}
}
