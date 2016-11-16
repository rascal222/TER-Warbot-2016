package edu.warbot.game.listeners;

import edu.warbot.agents.WarAgent;

/**
 * TeamListener. L'implémentation de cette interface permet de gérer
 * l'ajout et la suppression d'agent dans une équipe lors d'une partie.
 */
public interface TeamListener {

    /**
     * Méthode à implémenter gérant l'ajout d'un nouvel agent
     *
     * @param newAgent le nouvel agent
     */
    void onAgentAdded(WarAgent newAgent);

    /**
     * Méthode à implémenter gérant la suppression d'un agent
     *
     * @param removedAgent l'agent supprimé
     */
    void onAgentRemoved(WarAgent removedAgent);
}
