package edu.warbot.agents.actions.constants;

import edu.warbot.launcher.WarGameConfig;

/**
 * Définition des actions pour un agent contrôlable
 */
public interface ControllableActions extends IdlerActions {

    /**
     * Distance maximum pour donner des ressources
     */
    double MAX_DISTANCE_GIVE = WarGameConfig.getMaxDistanceGive();

    /**
     * Action "give" (ou donner) pour distribuer des ressources à un autre agent
     */
    String ACTION_GIVE = "give";

    /**
     * Action "eat" (ou manger) pour utiliser des ressources sur l'agent
     */
    String ACTION_EAT = "eat";


    String ACTION_DIE = "die";

}
