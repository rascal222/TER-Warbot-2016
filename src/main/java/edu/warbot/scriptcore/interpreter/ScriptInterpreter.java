package edu.warbot.scriptcore.interpreter;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.scriptcore.script.Script;
import edu.warbot.scriptcore.scriptagent.ScriptAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ScriptInterpreter {

    public void addScript(InputStream file, WarAgentType agent) throws IOException;

    public void addScript(StringBuilder file, WarAgentType agent);

    public void addSCript(Script script, WarAgentType agent);

    public Map<WarAgentType, Script> getScripts();

    public Script getScript(WarAgentType agent);

    public ScriptAgent giveScriptAgent(WarAgentType agent);

    public Script createScript(File file) throws FileNotFoundException;

    public ScriptAgent giveScriptAgent(Script script, String agentName);

    public Object giveScriptToolsAgent(Script script, String toolName);

    public Script createScript(InputStream file) throws IOException;
}

	
