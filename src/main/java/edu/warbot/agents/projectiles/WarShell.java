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

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

public class WarShell extends WarProjectile {
	
	private static final double NOT_CROSSING_ANOTHER_AGENT = -1;
    protected static double STEP_CHECK_CROSSING_ANOTHER_AGENT = 0.5; // A modifier selon la vitesse et la taille du projectile.
    protected WarAgent agentCollided = null; // Variable calculée, le premier agent touché par le projectile dans sa course.
    protected double distanceCrossingAnotherAgent = 0; // Variable calculée, distance à laquelle le premier agent est touché.   

    public static final double SPEED;
    public static final double EXPLOSION_RADIUS;
    public static final int AUTONOMY;
    public static final int DAMAGE;
    public static final double RANGE;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarShell);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        EXPLOSION_RADIUS = (double) data.get(WarGameConfig.PROJECTILE_CONFIG_EXPLOSION_RADIUS);
        AUTONOMY = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_AUTONOMY);
        DAMAGE = (int) data.get(WarGameConfig.PROJECTILE_CONFIG_DAMAGE);
        RANGE = SPEED * AUTONOMY;
    }


    public WarShell(InGameTeam inGameTeam, WarAgent sender) {
        super(ACTION_MOVE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarShell), sender, SPEED, EXPLOSION_RADIUS, DAMAGE, AUTONOMY);
    }

    public WarAgentType getType() {
        return WarAgentType.WarShell;
    }
    
    @Override
    protected void doBeforeEachTick() {
        super.doBeforeEachTick();
        checkCrossingAnotherAgent();
    }
    
    @Override
    public void kill() {
    	super.kill();
    	
    	if (agentCollided != null) {
    		if (agentCollided instanceof AliveWarAgent) {
    			((AliveWarAgent) agentCollided).damage(getDamage());
    		}
    	}
    }
    
    @Override
    public boolean isBlocked() {
        return isGoingToBeOutOfMap() || (distanceCrossingAnotherAgent != NOT_CROSSING_ANOTHER_AGENT);
    }
    
    @Override
    public String move() {
        logger.log(Level.FINEST, this.toString() + " moving...");
        if (isBlocked()) { // Le projectile sort de la map ou entre en collision avec un autre agent
        	kill();
        } else { // Sinon, il continue sa course
        	fd(SPEED);
        }
        return ACTION_MOVE;
    }
    
    protected void checkCrossingAnotherAgent() {
    	ArrayList<WarAgent> potentialTargets = getTeam().getGame().getAllAgentsInRadiusOf(this, getHitboxMaxRadius() + getSpeed());
    	boolean collided = false, complete = false;
    	int i = 0;
    	while (!(collided || complete)) {
    		if (i == potentialTargets.size()) {
    			distanceCrossingAnotherAgent = NOT_CROSSING_ANOTHER_AGENT;
    	        agentCollided = null;
    			complete = true;
    		} else {
    			WarAgent a = potentialTargets.get(i);
    			if (authorizedCollision(a)) { 
	        		
	        		double currentStep = 0;
	                while (currentStep < getSpeed()) {
	                    if (isInCollisionWithAtPosition(
	                            WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(currentStep, getHeading())),
	                            a)) {
	                    	distanceCrossingAnotherAgent = currentStep;
	                    	agentCollided = a;
	                    	collided = true;
	                    }
	                    currentStep += STEP_CHECK_CROSSING_ANOTHER_AGENT;
	                }
	                
	                if (isInCollisionWithAtPosition(WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(getSpeed(), getHeading())), a)) {
	                	distanceCrossingAnotherAgent = currentStep;
	                	agentCollided = a;
	                	collided = true;
	                }
    			}
	            i++;
    		}    		
    	}        
    }
    
    private boolean authorizedCollision(WarAgent a) { // Evite les collisions involontaires (auto-destruction, avec son propriétaire, avec un autre projectile...).
    	return a.getID() != sender.getID() 
    			&& a.getID() != getID()
    			&& !(a instanceof WarProjectile);
    }

}
