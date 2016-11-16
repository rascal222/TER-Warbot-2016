package edu.warbot.scriptcore.interpreter.javascript;

import edu.warbot.scriptcore.interpreter.ScriptInterpreter;
import edu.warbot.scriptcore.interpreter.ScriptInterpreterFactory;

public class JsScriptInterpreterFactory extends ScriptInterpreterFactory {

    public JsScriptInterpreterFactory() {
    }

    @Override
    public ScriptInterpreter createScriptInterpreter() {
        JsScriptInterpreter interpreter = new JsScriptInterpreter();
        return interpreter;
    }


}
