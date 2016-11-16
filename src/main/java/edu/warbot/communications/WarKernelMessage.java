package edu.warbot.communications;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import madkit.kernel.Message;

/**
 * Contient toutes les données sur l'émetteur d'un message et sur le contenu du message
 */
@SuppressWarnings("serial")
public class WarKernelMessage extends Message {

    private double _xSender;
    private double _ySender;
    private WarAgent sender;
    private int _idSender;
    private InGameTeam senderInGameTeam;
    private WarAgentType _senderType;
    private String _message;
    private String[] _content;

    public WarKernelMessage(WarAgent sender, String message, String[] content) {
        super();
        _message = message;
        _content = content;
        _xSender = sender.getX();
        _ySender = sender.getY();
        _idSender = sender.getID();
        senderInGameTeam = sender.getTeam();
        _senderType = WarAgentType.valueOf(sender.getClass().getSimpleName());
        this.sender = sender;
    }

    /**
     * Méthode renvoyant la coordonnée X où se trouve l'agent émetteur du message.
     *
     * @return {@code int} - la coordonnée X où se trouve l'agent émetteur du message.
     */
    public double getXSender() {
        return _xSender;
    }

    /**
     * Méthode renvoyant la coordonée Y où se trouve l'agent émetteur du message.
     *
     * @return {@code int} - la coordonée Y où se trouve l'agent émetteur du message.
     */
    public double getYsender() {
        return _ySender;
    }

    /**
     * Méthode renvoyant l'identifiant de l'agent émetteur du message.
     *
     * @return {@code int} - l'identifiant de l'agent émetteur du message.
     */
    public int getSenderID() {
        return _idSender;
    }

    /**
     * Méthode renvoyant l'équipe de l'agent émetteur du message.
     *
     * @return {@code InGameTeam} - l'équipe de l'agent émetteur du message.
     */
    public InGameTeam getSenderTeam() {
        return senderInGameTeam;
    }

    /**
     * Méthode renvoyant le type de l'agent émetteur du message.
     *
     * @return {@code WarAgentType} - le type de l'agent émetteur du message.
     */
    public WarAgentType getType() {
        return _senderType;
    }

    /**
     * Méthode renvoyant le message envoyé.
     *
     * @return {@code String} - le message envoyé.
     */
    public String getMessage() {
        return _message;
    }

    /**
     * Méthode renvoyant le contenu du message.
     *
     * @return {@code String[]} - contenu du message.
     */
    public String[] getContent() {
        return _content;
    }

    /**
     * Renvoie un adaptateur de WarKernelMessage.
     *
     * @return {@code WarMessage} - Adaptateur du WarKernelMessage
     */
    public WarMessage getAdapter(WarAgent receiver) {
        return new WarMessage(this, receiver);
    }

    public WarAgent getMessageSender() {
        return sender;
    }
}
