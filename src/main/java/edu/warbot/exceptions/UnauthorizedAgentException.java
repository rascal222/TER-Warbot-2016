package edu.warbot.exceptions;

public class UnauthorizedAgentException extends Exception {

	public UnauthorizedAgentException(String inGameTeam, String agentType) {
		super("Team '" + inGameTeam + "' cannot instantiate a '" + agentType + "' in this gameMode");
	}
	
	private static final long serialVersionUID = -8312920723144510789L;
}
