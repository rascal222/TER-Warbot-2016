package edu.warbot.scriptcore.interpreter.python;

import edu.warbot.scriptcore.interpreter.ScriptInterpreter;
import edu.warbot.scriptcore.interpreter.ScriptInterpreterFactory;

public class PyScriptInterpreterFactory extends ScriptInterpreterFactory {


    public PyScriptInterpreterFactory() {
    }

    @Override
    public ScriptInterpreter createScriptInterpreter() {
        PyScriptInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);
        PyScriptInterpreter interpreter = new PyScriptInterpreter();
        interpreter.initConfigurationFilePython();
        interpreter.initConfigurationTeamPython();
        return interpreter;
    }

}
