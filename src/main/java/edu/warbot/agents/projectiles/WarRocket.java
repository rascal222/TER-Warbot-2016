package edu.warbot.agents.projectiles;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarProjectile;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class WarRocket extends WarProjectile {

    public static final double SPEED;
    public static final double EXPLOSION_RADIUS;
    public static final int AUTONOMY;
    public static final int DAMAGE;
    public static final double RANGE;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarRocket);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        EXPLOSION_RADIUS = (double) data.get(WarGameConfig.PROJECTILE_CONFIG_EXPLOSION_RADIUS);
        AUTONOMY = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_AUTONOMY);
        DAMAGE = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_DAMAGE);
        RANGE = SPEED * AUTONOMY;
    }
    
    private double _targetDistance;

    public WarRocket(InGameTeam inGameTeam, WarAgent sender, double targetDistance) {
        super(ACTION_MOVE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarRocket), sender, SPEED, EXPLOSION_RADIUS, DAMAGE, AUTONOMY);
        _targetDistance = targetDistance + SPEED;
    }

    public WarAgentType getType() {
        return WarAgentType.WarRocket;
    }
    
    @Override
    protected void doBeforeEachTick() {
        super.doBeforeEachTick();
        _targetDistance -= SPEED;
    }
    
    @Override
    public String move() {
        logger.log(Level.FINEST, this.toString() + " moving...");
        if (isBlocked()) { // Le projectile sort de la map
        	kill();
        } else if (_targetDistance < SPEED) { // Le projectile a atteint sa destination
        	fd(_targetDistance);
        	kill();
        } else { // Le projectile continue
        	fd(SPEED);
        }
        return ACTION_MOVE;
    }
    
    @Override
    public void kill() {
        super.kill();
        // On va infliger des dégâts à tous les agents dans le radius de l'explosion
        List<WarAgent> touchedAgents = getTeam().getGame().getAllAgentsInRadiusOf(this, getExplosionRadius());
        for (WarAgent agent : touchedAgents) {
            if (agent instanceof AliveWarAgent) {
                ((AliveWarAgent) agent).damage(getDamage());
            }
        }
    }
    
}
