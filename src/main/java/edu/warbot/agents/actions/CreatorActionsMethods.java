package edu.warbot.agents.actions;


import edu.warbot.agents.actions.constants.CreatorActions;

/**
 * Définition des actions de création d'unité par méthode
 * <p/>
 * Double utilisation :
 * <ul>
 * <li> Dans les agents Madkit pour définir le code lors d'un appel d'Activator</li>
 * <li> Dans les "cerveaux" (brains) pour cacher la constante Action</li>
 * </ul>
 */
public interface CreatorActionsMethods extends CreatorActions {

    String create();
}
