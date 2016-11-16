package edu.warbot.scriptcore.scriptagent;

import edu.warbot.brains.WarBrain;

public class JsAgent implements ScriptAgent {

    private ScriptAgent script;

    public JsAgent(ScriptAgent script) {
        this.script = script;
    }

    @Override
    public String action() {
        return script.action();
    }

    @Override
    public void link(WarBrain brain) {
        script.link(brain);
    }

}
