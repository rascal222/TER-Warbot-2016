package edu.warbot.scriptcore.scriptagent;


import edu.warbot.brains.WarBrain;

public interface ScriptAgent {

    public String action();

    public void link(WarBrain brain);
}
