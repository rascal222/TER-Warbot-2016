package edu.warbot.agents;

import edu.warbot.agents.actions.IdlerActionsMethods;
import edu.warbot.brains.capacities.Alive;
import edu.warbot.game.InGameTeam;

import java.util.logging.Level;

/**
 * Définition d'un agent vivant de Warbot
 */
public abstract class AliveWarAgent extends WarAgent implements Alive, IdlerActionsMethods {

    /**
     * Coût de l'agent
     */
    private int _cost;
    /**
     * Niveau de santé courant
     */
    private int _health;
    /**
     * Niveau de santé maximum
     */
    private int _maxHealth;
    /**
    * Armure de l'agent
    */
   private int _armor;

    /**
     * @param firstActionToDo l'action à réaliser au démarrage
     * @param inGameTeam      l'équipe de l'agent
     * @param hitbox          la hitbox de l'agent
     * @param cost            le coût de l'agent
     * @param maxHealth       la vie maximum d'un agent
     */
    public AliveWarAgent(String firstActionToDo, InGameTeam inGameTeam, Hitbox hitbox, int cost, int maxHealth, int armor) {
        super(firstActionToDo, inGameTeam, hitbox);
        _cost = cost;
        _maxHealth = maxHealth;
        _health = _maxHealth;
        _armor = armor;
    }

    /**
     *
     * @return le coût de l'agent
     */
    public int getCost() {
        return _cost;
    }

    /**
     *
     * @return le niveau de vie courant
     */
    @Override
    public int getHealth() {
        return _health;
    }

    /**
     *
     * @return le niveau de vie maximum
     */
    @Override
    public int getMaxHealth() {
        return _maxHealth;
    }

    /**
     * Soigne l'agent d'une certaine quantité
     * @param quantity quantité de points de vie restaurée
     */
    public void heal(int quantity) {
        logger.log(Level.FINEST, this.toString() + "healed of " + quantity + "life points.");
        _health += quantity;
        if (_health > getMaxHealth()) {
            _health = getMaxHealth();
        }
    }

    /**
     * Endommage l'agent d'une certaine quantité
     * @param quantity quantité de dégâts infligés
     */
    public void damage(int quantity) {
    	int damages = 0;
    	if(quantity - _armor > 0)
    		damages = (quantity - _armor);
        logger.finest(this.toString() + "damaged of " + damages + "life points.");
        _health -= damages;
        if (_health <= 0) {
            logger.finer(this.toString() + "killed.");
            kill();
        }
    }

    @Override
    public void kill() {
        getTeam().setWarAgentAsDying(this);
    }

    public void init(int health) {
        _health = health;
    }
}
