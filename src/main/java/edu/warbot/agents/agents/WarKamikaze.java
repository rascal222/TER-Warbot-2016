package edu.warbot.agents.agents;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.actions.AgressiveActionsMethods;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.projectiles.WarBomb;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Agressive;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;
import java.util.logging.Level;

public class WarKamikaze extends MovableWarAgent implements AgressiveActionsMethods, Agressive {

    public static final double ANGLE_OF_VIEW;
    public static final double DISTANCE_OF_VIEW;
    public static final int COST;
    public static final int MAX_HEALTH;
    public static final int BAG_SIZE;
    public static final double SPEED;
    public static final int ARMOR;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarKamikaze);
        ANGLE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_ANGLE_OF_VIEW);
        DISTANCE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_DISTANCE_OF_VIEW);
        COST = (int) data.get(WarGameConfig.AGENT_CONFIG_COST);
        MAX_HEALTH = (int) data.get(WarGameConfig.AGENT_CONFIG_MAX_HEALTH);
        BAG_SIZE = (int) data.get(WarGameConfig.AGENT_CONFIG_BAG_SIZE);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        ARMOR = (int) data.get(WarGameConfig.AGENT_CONFIG_ARMOR);
    }

    public WarKamikaze(InGameTeam inGameTeam, WarBrain brain) {
        super(ACTION_IDLE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarKamikaze), brain, DISTANCE_OF_VIEW, ANGLE_OF_VIEW, COST, MAX_HEALTH, BAG_SIZE, SPEED, ARMOR);

//		brain.setAgentAdapter(new WarKamikazeBrain(this));
    }

    @Override
    public String fire() {
        logger.log(Level.FINER, this.toString() + " fired.");
        launchAgent(new WarBomb(getTeam(), this));
        kill();
        return getBrain().action();
    }

    @Override
    public String beginReloadWeapon() {
        return getBrain().action();
    }

    @Override
    public boolean isReloaded() {
        return true;
    }

    @Override
    public boolean isReloading() {
        return false;
    }

    public WarAgentType getType() {
        return WarAgentType.WarKamikaze;
    }
}
