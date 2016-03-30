package edu.warbot.agents;

import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.game.InGameTeam;

import java.util.logging.Level;

/**
 * Définition d'un agent Warbot de type projectile (ou explosif)
 */
public abstract class WarProjectile extends WarAgent implements MovableActionsMethods, Movable {
        
	private double speed;
    private double explosionRadius;
    private int damage;
    private int autonomy;
    protected WarAgent sender;
    private int currentAutonomy;

    public WarProjectile(String firstActionToDo, InGameTeam inGameTeam, Hitbox hitbox, WarAgent sender, double speed, double explosionRadius, int damage, int autonomy) {
        super(firstActionToDo, inGameTeam, hitbox);

        this.speed = speed;
        this.explosionRadius = explosionRadius;
        this.damage = damage;
        this.autonomy = autonomy;
        this.currentAutonomy = autonomy;
        this.sender = sender;
    }

    @Override
    protected void activate() {
        super.activate();
        setHeading(sender.getHeading());
        setXY(sender.getX(), sender.getY());
    }

    @Override
    protected void doBeforeEachTick() {
        super.doBeforeEachTick();
        currentAutonomy--;
        if (currentAutonomy < 0)
            kill();
    }

    @Override
    public String move() {
        logger.log(Level.FINEST, this.toString() + " moving...");
        if (!isBlocked()) { // Vérifie que le projectile ne sort pas de la map
            fd(getSpeed());
        } else {
        	kill();
        }
        return ACTION_MOVE;
    }

    @Override
    public void kill() {
    	if (isAlive()) {
    		getTeam().setWarAgentAsDying(this);
    	}        
    }

    @Override
    public boolean isBlocked() {
        return isGoingToBeOutOfMap();
    }

    public double getSpeed() {
        return speed;
    }

    public double getExplosionRadius() {
        return explosionRadius;
    }

    public int getDamage() {
        return damage;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public int getCurrentAutonomy() {
        return currentAutonomy;
    }
}
