package edu.warbot.game.modes.endCondition;

@SuppressWarnings("serial")
public class IncoherenceNumberAgentException extends Exception {

    public IncoherenceNumberAgentException(WarNumberAgent numberAgent) {
        super("We have a incoherence with min and max in WarNumberAgent : " + numberAgent.getAgent().toString());
    }
}