package edu.warbot.scriptcore.script;

import edu.warbot.scriptcore.interpreter.ScriptInterpreterLanguage;

public class PyScript extends Script {


    public PyScript(StringBuilder file) {
        super(ScriptInterpreterLanguage.PYTHON);
        setCodeAgent(file);
    }

}
