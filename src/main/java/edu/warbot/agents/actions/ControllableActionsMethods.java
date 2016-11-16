package edu.warbot.agents.actions;

import edu.warbot.agents.actions.constants.ControllableActions;

/**
 * Définition des actions de base d'un agent Warbot par méthode
 * <p/>
 * Double utilisation :
 * <ul>
 * <li> Dans les agents Madkit pour définir le code lors d'un appel d'Activator</li>
 * <li> Dans les "cerveaux" (brains) pour cacher la constante Action</li>
 * </ul>
 */
public interface ControllableActionsMethods extends IdlerActionsMethods, ControllableActions {

    String give();

    String eat();

    String die();
}
