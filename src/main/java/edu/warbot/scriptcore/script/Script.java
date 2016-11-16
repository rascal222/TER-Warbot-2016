package edu.warbot.scriptcore.script;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.ScriptedTeam;
import edu.warbot.scriptcore.exceptions.DangerousFunctionPythonException;
import edu.warbot.scriptcore.interpreter.ScriptInterpreterLanguage;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Script {

    private ScriptInterpreterLanguage langage;
    private StringBuilder codeAgent;

    public Script(ScriptInterpreterLanguage langage) {
        super();
        this.langage = langage;
    }

    public static Script checkDangerousFunctions(ScriptedTeam team, InputStream input, WarAgentType agent) throws IOException, DangerousFunctionPythonException {

        Script sc = team.getInterpreter().createScript(input);
        String lg = "";
        ScriptInterpreterLanguage lang = sc.getLangage();

        if (lang.equals(ScriptInterpreterLanguage.PYTHON))
            lg = "def";
        else if (lang.equals(ScriptInterpreterLanguage.JAVASCRIPT))
            lg = "var";
        else if (lang.equals(ScriptInterpreterLanguage.RUBY))
            lg = "def";

        for (String s : ScriptedTeam.getFunctions()) {
            Pattern p = Pattern.compile(".*(" + lg + ")\\s+(" + s + ")");
            Matcher m = p.matcher(sc.getCodeAgent().toString());
            if (m.find())
                throw new DangerousFunctionPythonException(s, agent.toString());
        }
        return sc;
    }

    public ScriptInterpreterLanguage getLangage() {
        return langage;
    }

    public StringBuilder getCodeAgent() {
        return codeAgent;
    }

    public void setCodeAgent(StringBuilder codeAgent) {
        this.codeAgent = codeAgent;
    }

    public void addCodeToAgent(StringBuilder code) {
        codeAgent.append(code);
    }

    public void addCodeToAgent(Script script) {
        codeAgent.append(script.getCodeAgent());
    }

    @Override
    public String toString() {
        return "Script [langage = " + langage + "]";
    }
}
