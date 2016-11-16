package edu.warbot.scriptcore.script;

import edu.warbot.scriptcore.interpreter.ScriptInterpreterLanguage;

public class JsScript extends Script {


    public JsScript(StringBuilder file) {
        super(ScriptInterpreterLanguage.JAVASCRIPT);
        setCodeAgent(file);
    }
}
