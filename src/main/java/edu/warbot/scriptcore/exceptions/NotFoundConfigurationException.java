package edu.warbot.scriptcore.exceptions;

/**
 * Created by jimmy on 12/03/15.
 */
@SuppressWarnings("serial")
public class NotFoundConfigurationException extends Exception {
    public NotFoundConfigurationException(String pythonFile) {
        super("We can't not found the configuration file python : " + pythonFile);
    }
}
