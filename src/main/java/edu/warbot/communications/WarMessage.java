package edu.warbot.communications;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.tools.geometry.CartesianCoordinates;

/**
 * WarMessage Est un adaptateur pour un WarKernelMessage. Permet à un utilisateur de ne pas accéder à toutes les méthodes de madkit.kernel.Message
 */
public class WarMessage {

    private double _angle;
    private double _distance;
    private WarKernelMessage _kernelMessage;

    public WarMessage(WarKernelMessage kernelMsg, WarAgent receiver) {
        _kernelMessage = kernelMsg;
        _angle = receiver.getPosition().getAngleToPoint(new CartesianCoordinates(kernelMsg.getXSender(), kernelMsg.getYsender()));
        _distance = receiver.getAverageDistanceFrom(kernelMsg.getMessageSender());
    }

    /**
     * Retourne l'angle depuis lequel le message a été reçu.
     *
     * @return {@code double} - l'angle depuis lequel le message a été reçu.
     */
    public double getAngle() {
        return _angle;
    }

    /**
     * Retourne la distance entre l'émetteur et le récepteur du message.
     *
     * @return {@code double} - la distance entre l'émetteur et le récepteur du message.
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * Retourne l'ID de l'agent émetteur.
     *
     * @return {@code int} - l'ID de l'agent émetteur.
     */
    public int getSenderID() {
        return _kernelMessage.getSenderID();
    }

    /**
     * Retourne le nom de l'équipe de l'agent émetteur.
     *
     * @return {@code String} - le nom de l'équipe de l'agent émetteur.
     */
    public String getSenderTeamName() {
        return _kernelMessage.getSenderTeam().getName();
    }

    /**
     * Retourne le type de l'agent émetteur.
     *
     * @return {@code WarAgentType} - le type de l'agent émetteur.
     */
    public WarAgentType getSenderType() {
        return _kernelMessage.getType();
    }

    /**
     * Retourne le message envoyé.
     *
     * @return {@code String} - le message envoyé.
     */
    public String getMessage() {
        return _kernelMessage.getMessage();
    }

    /**
     * Retourne le contenu du message.
     *
     * @return {@code String[]} - contenu du message.
     */
    public String[] getContent() {
        return _kernelMessage.getContent();
    }


    /**
     * Affiche des informations sur le message
     */
    public String toString() {
        return "--- Message : " + getSenderType() + " " + getSenderID() +
                " - Equipe : " + getSenderTeamName() + " - " + getAngle() + "° " + getDistance() + " => " + getMessage();
    }
}
