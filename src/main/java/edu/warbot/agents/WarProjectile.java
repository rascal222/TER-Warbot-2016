package edu.warbot.agents;

import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.game.InGameTeam;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import java.util.List;
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
            explode();
    }

    @Override
    public String move() {
        logger.log(Level.FINEST, this.toString() + " moving...");
        if (!isBlocked()) {
            fd(getSpeed());
        } else {
            explode();
        }
        return ACTION_MOVE;
    }

    protected void explode() {
        if (isAlive()) {
//			killAgent(this);
            kill();
            // On va infliger des dégâts à tous les agents dans le radius de l'explosion
            List<WarAgent> touchedAgents = getTeam().getGame().getAllAgentsInRadiusOf(this, explosionRadius);
            for (WarAgent agent : touchedAgents) {
                if (agent instanceof AliveWarAgent) {
                    ((AliveWarAgent) agent).damage(damage);
                } else if (agent instanceof WarProjectile) {
                    ((WarProjectile) agent).explode();
                }
            }
        }
    }

    @Override
    public void kill() {
        getTeam().setWarAgentAsDying(this);
    }

    protected boolean isGoingToCrossAnOtherAgent() {
        for (WarAgent a : getTeam().getGame().getAllAgentsInRadiusOf(this, getHitboxMaxRadius() + getSpeed())) {
            if (a.getID() != sender.getID() && a.getID() != getID()) {
                double currentStep = 0;
                while (currentStep < getSpeed()) {
                    if (isInCollisionWithAtPosition(
                            WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(currentStep, getHeading())),
                            a)) {
                        return true;
                    }
                    currentStep += 1.0;
                }
                
                return isInCollisionWithAtPosition(
                        WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(getSpeed(), getHeading())),
                        a);
            }
        }
        return false;
    }

    @Override
    public boolean isBlocked() {
        return isGoingToBeOutOfMap() || isGoingToCrossAnOtherAgent();
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
