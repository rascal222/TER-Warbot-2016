package edu.warbot.agents.agents;

import edu.warbot.agents.CreatorWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;

public class WarBase extends CreatorWarAgent {

    public static final double ANGLE_OF_VIEW;
    public static final double DISTANCE_OF_VIEW;
    public static final int COST;
    public static final int MAX_HEALTH;
    public static final int BAG_SIZE;
    public static final int ARMOR;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarBase);
        ANGLE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_ANGLE_OF_VIEW);
        DISTANCE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_DISTANCE_OF_VIEW);
        COST = (int) data.get(WarGameConfig.AGENT_CONFIG_COST);
        MAX_HEALTH = (int) data.get(WarGameConfig.AGENT_CONFIG_MAX_HEALTH);
        BAG_SIZE = (int) data.get(WarGameConfig.AGENT_CONFIG_BAG_SIZE);
        ARMOR = (int) data.get(WarGameConfig.AGENT_CONFIG_ARMOR);
    }

    public WarBase(InGameTeam inGameTeam, WarBrain brain) {
        super(ACTION_IDLE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarBase), brain, DISTANCE_OF_VIEW, ANGLE_OF_VIEW, COST, MAX_HEALTH, BAG_SIZE, ARMOR);
    }

    @Override
    public boolean isAbleToCreate(WarAgentType agent) {
        if (agent == WarAgentType.WarExplorer || agent == WarAgentType.WarEngineer || agent == WarAgentType.WarKamikaze ||
                agent == WarAgentType.WarRocketLauncher)
            return true;
        return false;
    }

    public WarAgentType getType() {
        return WarAgentType.WarBase;
    }
}
