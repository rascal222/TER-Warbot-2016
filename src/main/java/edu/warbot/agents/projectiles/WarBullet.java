package edu.warbot.agents.projectiles;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarProjectile;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import java.util.Map;

public class WarBullet extends WarProjectile {

    public static final double SPEED;
    public static final double EXPLOSION_RADIUS;
    public static final int AUTONOMY;
    public static final int DAMAGE;
    public static final double RANGE;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarBullet);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        EXPLOSION_RADIUS = (double) data.get(WarGameConfig.PROJECTILE_CONFIG_EXPLOSION_RADIUS);
        AUTONOMY = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_AUTONOMY);
        DAMAGE = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_DAMAGE);
        RANGE = SPEED * AUTONOMY;
    }


    public WarBullet(InGameTeam inGameTeam, WarAgent sender) {
        super(ACTION_MOVE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarBullet), sender, SPEED, EXPLOSION_RADIUS, DAMAGE, AUTONOMY);
    }

    public WarAgentType getType() {
        return WarAgentType.WarBullet;
    }
    
    @Override
    public void explode() {
    	if (isAlive()) {
    		kill();
    		for (WarAgent a : getTeam().getGame().getAllAgentsInRadiusOf(this, getHitboxMaxRadius() + getSpeed())) {
                if (a.getID() != sender.getID() && a.getID() != getID()) {
                    double currentStep = 0;
                    while (currentStep < getSpeed()) {
                        if (isInCollisionWithAtPosition(
                                WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(currentStep, getHeading())),
                                a)) {
                        	if (a instanceof AliveWarAgent) {
                                ((AliveWarAgent) a).damage(getDamage());
                            }
                        }
                        currentStep += 1.0;
                    }
                    
                    if (isInCollisionWithAtPosition(
                            WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(currentStep, getHeading())),
                            a)) {
                    	if (a instanceof AliveWarAgent) {
                            ((AliveWarAgent) a).damage(getDamage());
                        }
                    }
                }
            }
    	}
    }

}
