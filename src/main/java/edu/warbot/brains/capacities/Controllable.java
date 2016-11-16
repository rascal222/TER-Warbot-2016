package edu.warbot.brains.capacities;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WallPercept;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.communications.WarMessage;
import edu.warbot.tools.geometry.PolarCoordinates;
import madkit.kernel.AbstractAgent.ReturnCode;

import java.awt.*;
import java.util.List;

/**
 * Définition de la capacité Controllable donnant la possibilité de créer le comportement des agents
 */
public interface Controllable extends Alive {

    /**
     * Envoie un message à un autre agent
     *
     * @param idAgent l'identifiant d'un agent
     * @param message le message à transmettre
     * @param content plusieurs chaînes de caractères définissant le contenu du message
     * @return une instance ReturnCode indiquant le succès (ou l'échec) de l'opération
     */
    ReturnCode sendMessage(int idAgent, String message, String... content);

    /**
     * Envoie un message à tout les agents de l'équipe
     * @param message le message à transmettre
     * @param content plusieurs chaînes de caractères définissant le contenu du message
     */
    void broadcastMessageToAll(String message, String... content);

    /**
     * Envoie un message à tout les agents d'un type
     * @param agentType le type d'agent visé
     * @param message le message à transmettre
     * @param content plusieurs chaînes de caractères définissant le contenu du message
     * @return une instance ReturnCode indiquant le succès (ou l'échec) de l'opération
     */
    ReturnCode broadcastMessageToAgentType(WarAgentType agentType, String message, String... content);

    /**
     * Envoie un message à un groupe et un rôle particulier
     * @param groupName le nom du groupe
     * @param roleName le nom d'un rôle
     * @param message le message à transmettre
     * @param content plusieurs chaînes de caractères définissant le contenu du message
     * @return une instance ReturnCode indiquant le succès (ou l'échec) de l'opération
     */
    ReturnCode broadcastMessage(String groupName, String roleName, String message, String... content);

    void broadcastMessageToGroup(String groupName, String message, String... content);


    ReturnCode reply(WarMessage warMessage, String message, String... content);

    /**
     * Vide et récupère la mailbox d'un agent
     *
     * @return la liste de messages transmis à l'agent
     */
    List<WarMessage> getMessages();

    /**
     * Définie l'agent auquel on va donner des ressources
     * @param idNextAgentToGive l'identifiant de l'agent
     */
    void setIdNextAgentToGive(int idNextAgentToGive);

    /**
     *
     * @return la taille maximum du sac
     */
    int getBagSize();

    /**
     *
     * @return le nombre d'éléments dans le bag
     */
    int getNbElementsInBag();

    /**
     *
     * @return vrai si et seulement si le sac est vide
     */
    boolean isBagEmpty();

    /**
     *
     * @return vrai si et seulement si le sac est plein
     */
    boolean isBagFull();

    /**
     *
     * @return l'angle de direction de la vue de l'agent
     */
    double getViewDirection();

    /**
     * Modifie l'angle de la vue
     * @param viewDirection le nouvel angle de direction de la vue
     */
    void setViewDirection(double viewDirection);

    /**
     * @return la liste des perceptions alliées
     */
    List<WarAgentPercept> getPerceptsAllies();

    /**
     * @return la liste des perceptions enemies
     */
    List<WarAgentPercept> getPerceptsEnemies();

    /**
     * @return la liste des ressources perçues
     */
    List<WarAgentPercept> getPerceptsResources();

    /**
     *
     * @param agentType le type d'agent à percevoir
     * @return la liste des perceptions alliées en fonction d'un type d'agent
     */
    List<WarAgentPercept> getPerceptsAlliesByType(WarAgentType agentType);

    /**
     *
     * @param agentType le type d'agent à percevoir
     * @return la liste des perceptions enemies en fonction d'un type d'agent
     */
    List<WarAgentPercept> getPerceptsEnemiesByType(WarAgentType agentType);

    /**
     *
     * @return la liste des perceptions
     */
    List<WarAgentPercept> getPercepts();

    /**
     *
     * @return la liste des perceptions de type mur
     */
    List<WallPercept> getWallPercepts();

    /**
     *
     * @return la chaîne de caractères pour le "debugging"
     */
    String getDebugString();

    /**
     * Modifie une chaîne de caractère pour le "debugging"
     * @param debugString une nouvelle chaîne de caractères
     */
    void setDebugString(String debugString);

    /**
     *
     * @return la couleur de la chaîne de caractères pour le "debugging"
     */
    Color getDebugStringColor();

    /**
     * Modifie la couleur de la chaîne de caractères pour le "debugging"
     * @param color une nouvelle couleur
     */
    void setDebugStringColor(Color color);

    /**
     *
     * @return
     */
    Shape getDebugShape();

    /**
     *
     * @param debugShape
     */
    void setDebugShape(Shape debugShape);

    /**
     * Donne la position polaire du barycentre entre plusieurs agents
     * @param agentType le type d'agent
     * @param ally vrai si on veut le barycentre parmi les alliés
     * @return
     */
    PolarCoordinates getAveragePositionOfUnitInPercept(WarAgentType agentType, boolean ally);

    /**
     *
     * @param message le message que l'on souhaite
     * @return
     */
    PolarCoordinates getIndirectPositionOfAgentWithMessage(WarMessage message);

    /**
     *
     * @param angleToAlly
     * @param distanceFromAlly
     * @param angleFromAllyToTarget
     * @param distanceBetweenAllyAndTarget
     * @return
     */
    PolarCoordinates getTargetedAgentPosition(double angleToAlly, double distanceFromAlly, double angleFromAllyToTarget, double distanceBetweenAllyAndTarget);


    List<String> myGroups();

    List<String> myRoles(String group);

}
