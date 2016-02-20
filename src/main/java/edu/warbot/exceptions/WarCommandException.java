package edu.warbot.exceptions;

import edu.warbot.launcher.WarMain;

/**
 * Created by beugnon on 18/06/15.
 */
@SuppressWarnings("serial")
public class WarCommandException extends Exception {

    public WarCommandException(String message) {
        super(message + "\nEnter \"" + WarMain.CMD_NAME + " " + WarMain.CMD_HELP + "\" for more informations.");
    }

}
