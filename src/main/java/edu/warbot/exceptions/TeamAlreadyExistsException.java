package edu.warbot.exceptions;

/**
 * Created by beugnon on 18/06/15.
 */
@SuppressWarnings("serial")
public class TeamAlreadyExistsException extends Exception {

    public TeamAlreadyExistsException(String teamName) {
        super("InGameTeam name '" + teamName + "' already used.");
    }
}
