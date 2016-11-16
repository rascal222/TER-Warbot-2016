package edu.warbot.agents.projectiles;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarProjectile;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;

public class WarDeathRocket extends WarProjectile {

    public static final double SPEED;
    public static final double EXPLOSION_RADIUS;
    public static final int AUTONOMY;
    public static final int DAMAGE;
    public static final double RANGE;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarDeathRocket);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        EXPLOSION_RADIUS = (double) data.get(WarGameConfig.PROJECTILE_CONFIG_EXPLOSION_RADIUS);
        AUTONOMY = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_AUTONOMY);
        DAMAGE = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_DAMAGE);
        RANGE = SPEED * AUTONOMY;
    }


    public WarDeathRocket(InGameTeam inGameTeam, WarAgent sender) {
        super(ACTION_MOVE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarDeathRocket), sender, SPEED, EXPLOSION_RADIUS, DAMAGE, AUTONOMY);
    }

    public WarAgentType getType() {
        return WarAgentType.WarDeathRocket;
    }
}
