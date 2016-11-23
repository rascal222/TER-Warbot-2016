package edu.warbot.agents.agents;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.actions.BuilderActionsMethods;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Builder;
import edu.warbot.brains.capacities.Building;
import edu.warbot.exceptions.UnauthorizedAgentException;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;

public class WarEngineer extends MovableWarAgent implements BuilderActionsMethods, Builder {

    public static final double ANGLE_OF_VIEW;
    public static final double DISTANCE_OF_VIEW;
    public static final int COST;
    public static final int MAX_HEALTH;
    public static final int BAG_SIZE;
    public static final double SPEED;
    public static final int MAX_REPAIRS_PER_TICK;
    public static final int ARMOR;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarEngineer);
        ANGLE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_ANGLE_OF_VIEW);
        DISTANCE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_DISTANCE_OF_VIEW);
        COST = (int) data.get(WarGameConfig.AGENT_CONFIG_COST);
        MAX_HEALTH = (int) data.get(WarGameConfig.AGENT_CONFIG_MAX_HEALTH);
        BAG_SIZE = (int) data.get(WarGameConfig.AGENT_CONFIG_BAG_SIZE);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        MAX_REPAIRS_PER_TICK = (int) data.get(WarGameConfig.AGENT_CONFIG_MAX_REPAIRS_PER_TICK);
        ARMOR = (int) data.get(WarGameConfig.AGENT_CONFIG_ARMOR);
    }

    private WarAgentType nextBuildingToBuild;
    private int idNextBuildingToRepair;

    public WarEngineer(InGameTeam inGameTeam, WarBrain brain) {
        super(ACTION_IDLE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarEngineer), brain, DISTANCE_OF_VIEW, ANGLE_OF_VIEW, COST, MAX_HEALTH, BAG_SIZE, SPEED, ARMOR);
        nextBuildingToBuild = WarAgentType.Wall;
    }

    @Override
    public String build() {
        try {
			getTeam().build(this, nextBuildingToBuild);
		} catch (UnauthorizedAgentException e) {
			System.err.println(e.getMessage());
		}
        return getBrain().action();
    }

    @Override
    public String repair() {
        WarAgent agentToRepair = getTeam().getAgentWithID(idNextBuildingToRepair);
        if (agentToRepair != null) {
            if ( (agentToRepair instanceof Building) && (getHealth() > ((Building) agentToRepair).getCostToRepair(MAX_REPAIRS_PER_TICK)) )
            {
                ((AliveWarAgent) agentToRepair).heal(MAX_REPAIRS_PER_TICK);
                damage(((Building) agentToRepair).getCostToRepair(MAX_REPAIRS_PER_TICK));
            }
        }
        return getBrain().action();
    }

    @Override
    public WarAgentType getNextBuildingToBuild() {
        return nextBuildingToBuild;
    }

    @Override
    public void setNextBuildingToBuild(WarAgentType nextBuildingToBuild) {
        this.nextBuildingToBuild = nextBuildingToBuild;
    }

    @Override
    public boolean isAbleToBuild(WarAgentType building) {
    	// TODO Ceci est un débug temporaire, le WarEngineer n'est pas censé construire de Base en DualGameMode).
        return (building.getCategory() == WarAgentCategory.Building && building != WarAgentType.WarBase);
    }

    @Override
    public int getIdNextBuildingToRepair() {
        return idNextBuildingToRepair;
    }

    @Override
    public void setIdNextBuildingToRepair(int idNextBuildingToRepair) {
        this.idNextBuildingToRepair = idNextBuildingToRepair;
    }

    public WarAgentType getType() {
        return WarAgentType.WarEngineer;
    }
}
