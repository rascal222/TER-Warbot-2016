package edu.warbot.agents.actions;

import edu.warbot.agents.actions.constants.IdlerActions;

/**
 * Définition des actions de base par méthode
 * <p/>
 * Double utilisation :
 * <ul>
 * <li> Dans les agents Madkit pour définir le code lors d'un appel d'Activator</li>
 * <li> Dans les "cerveaux" (brains) pour cacher la constante Action</li>
 * </ul>
 */
public interface IdlerActionsMethods extends IdlerActions {

    String idle();

}
