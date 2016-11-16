package edu.warbot.brains.capacities;

import edu.warbot.agents.percepts.WarAgentPercept;
import madkit.kernel.AbstractAgent;

/**
 *  * Définition des capacités communes aux agents Warbot

 */
public interface CommonCapacities {

    /**
     * @param group
     * @param role
     * @return
     */
    AbstractAgent.ReturnCode requestRole(String group, String role);

    /**
     *
     * @param group
     * @param role
     * @return
     */
    AbstractAgent.ReturnCode leaveRole(String group, String role);

    /**
     *
     * @param group
     * @return
     */
    AbstractAgent.ReturnCode leaveGroup(String group);

    /**
     *
     * @param group
     * @param role
     * @return
     */
    int getNumberOfAgentsInRole(String group, String role);

    /**
     *
     * @return la direction de l'agent en degrés
     */
    double getHeading();

    /**
     * Change la direction de l'agent en degrés
     * @param angle le nouvel angle de l'agent
     */
    void setHeading(double angle);

    /**
     * Mets une direction aléatoire à l'agent
     */
    void setRandomHeading();

    /**
     * Mets une direction alétoire à l'agent dans un intervalle
     * @param range l'intervalle pour l'angle aléatoire
     */
    void setRandomHeading(int range);

    /**
     *
     * @return le nom de l'équipe
     */
    String getTeamName();

    /**
     *
     * @param percept la perception d'un agent
     * @return Vrai si et seulement si l'agent perçu est un ennemi
     */
    boolean isEnemy(WarAgentPercept percept);

    /**
     *
     * @return l'identifiant d'un agent
     */
    int getID();
}
