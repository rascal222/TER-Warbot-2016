package edu.warbot.scriptcore.team;

import edu.warbot.brains.brains.WarExplorerBrain;
import edu.warbot.scriptcore.scriptagent.ScriptAgent;

import java.awt.*;

public abstract class ScriptableWarExplorer extends WarExplorerBrain implements Scriptable {


    private ScriptAgent script;

    public ScriptableWarExplorer() {
        super();
    }


    @Override
    public final String action() {
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