package edu.warbot.brains;


import edu.warbot.agents.actions.ControllableActionsMethods;
import edu.warbot.agents.actions.IdlerActionsMethods;
import edu.warbot.agents.actions.constants.ControllableActions;
import edu.warbot.brains.capacities.Controllable;

/**
 * Définition d'un "cerveau" d'agent Warbot
 */
public abstract class WarBrain implements ControllableActions, Controllable, ControllableActionsMethods, IdlerActionsMethods {


    public WarBrain() {
    }

    public void activate() {
    }

    /**
     * @return l'action de l'agent
     */
    public String action() {
        return idle();
    }

    /**
     *
     * @return l'action "idle" (ou ne rien faire)
     */
    @Override
    public final String idle() {
        return ACTION_IDLE;
    }

    /**
     *
     * @return l'action "give" (ou donner un objet)
     */
    @Override
    public String give() {
        return ACTION_GIVE;
    }

    /**
     *
     * @return l'action "eat" (ou manger)
     */
    @Override
    public String eat() {
        return ACTION_EAT;
    }

    public String die() { return "die"; }
}
