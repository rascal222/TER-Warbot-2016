package edu.warbot.scriptcore.exceptions;

/**
 * Created by jimmy on 18/03/15.
 */
@SuppressWarnings("serial")
public class DangerousFunctionPythonException extends Exception {

    public DangerousFunctionPythonException(String function, String brainType) {
        super("La fonction " + function + " ne peut etre définie dans le code de l'agent "
                + brainType + " car déjà définie");
    }
}
