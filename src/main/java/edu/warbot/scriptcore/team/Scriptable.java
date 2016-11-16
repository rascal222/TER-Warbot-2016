package edu.warbot.scriptcore.team;

import edu.warbot.scriptcore.scriptagent.ScriptAgent;

public interface Scriptable {

    public ScriptAgent getScriptAgent();

    public void setScriptAgent(ScriptAgent script);
}