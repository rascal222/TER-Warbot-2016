package edu.warbot.scriptcore.team;

import edu.warbot.brains.brains.WarTurretBrain;
import edu.warbot.scriptcore.scriptagent.ScriptAgent;

import java.awt.*;

public abstract class ScriptableWarTurret extends WarTurretBrain implements Scriptable {

    private ScriptAgent script;

    public ScriptableWarTurret() {
        super();
    }

    @Override
    public void activate() {

    }

    @Override
    public String action() {
        String act = null;
        try {
            act = getScriptAgent().action();
        } catch (Exception e) {
            setDebugStringColor(Color.RED);
            setDebugString("Erreur à l'interprétation");
            e.printStackTrace();
        }

        return act;
    }

    public ScriptAgent getScriptAgent() {
        return script;
    }

    public void setScriptAgent(ScriptAgent script) {
        this.script = script;
    }
}
