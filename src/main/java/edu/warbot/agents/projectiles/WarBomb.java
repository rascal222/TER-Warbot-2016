package edu.warbot.agents.projectiles;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarProjectile;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;

public class WarBomb extends WarProjectile {

    public static final double SPEED;
    public static final double EXPLOSION_RADIUS;
    public static final int AUTONOMY;
    public static final int DAMAGE;
    public static final double RANGE;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarBomb);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        EXPLOSION_RADIUS = (double) data.get(WarGameConfig.PROJECTILE_CONFIG_EXPLOSION_RADIUS);
        AUTONOMY = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_AUTONOMY);
        DAMAGE = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_DAMAGE);
        RANGE = SPEED * AUTONOMY;
    }

    public WarBomb(InGameTeam inGameTeam, WarAgent sender) {
        super(ACTION_MOVE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarBomb), sender, SPEED, EXPLOSION_RADIUS, DAMAGE, AUTONOMY);
    }

    public WarAgentType getType() {
        return WarAgentType.WarBomb;
    }

//	@Override
//	protected void explode() {
//        if (isAlive()) {
//            kill();
//            // On va infliger des dégâts à tous les agents dans le radius de l'explosion
//            List<WarAgent> touchedAgents = getTeam().getGame().getAllAgentsInRadiusOf(this, getExplosionRadius());
//            for (WarAgent agent : touchedAgents) {
//                if (agent instanceof AliveWarAgent) {
//                    ((AliveWarAgent) agent).damage(getDamage());
//                }
//            }
//        }
//    }

}
