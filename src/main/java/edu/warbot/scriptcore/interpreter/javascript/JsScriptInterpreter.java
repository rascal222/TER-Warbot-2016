package edu.warbot.scriptcore.interpreter.javascript;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.scriptcore.interpreter.ScriptInterpreter;
import edu.warbot.scriptcore.script.JsScript;
import edu.warbot.scriptcore.script.Script;
import edu.warbot.scriptcore.scriptagent.ScriptAgent;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class JsScriptInterpreter implements ScriptInterpreter {

    private static Map<String, Script> jsScriptsFirst = new HashMap<String, Script>();
    private static Map<String, Script> jsScriptsSecond = new HashMap<String, Script>();
    private static Map<String, Script> jsScriptsThird = new HashMap<String, Script>();
    private static boolean initConfiguration = false;
    protected Map<WarAgentType, Script> scripts;
    protected ScriptEngine engine;

    public JsScriptInterpreter() {
        super();
        this.scripts = new HashMap<WarAgentType, Script>();
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("javascript");
        //Nashorn/Rhino Compatibility
        if (manager.getEngineByName("Nashorn") != null) {
            try {
                InputStream is = getFileJavascript("scripts/javascript/NashornCompatibility.js");
                Script compatibility = createScript(is);
                is.close();
                evalClass(compatibility);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        if (!initConfiguration) {
            InputStream jsClassExtend = getFileJavascript("scripts/javascript/ClassExtend.js");

            InputStream jsWarAgent = getFileJavascript("scripts/javascript/JsWarAgent.js");

            InputStream jsWarBase = getFileJavascript("scripts/javascript/JsWarBase.js");
            InputStream jsWarEngineer = getFileJavascript("scripts/javascript/JsWarEngineer.js");
            InputStream jsWarExplorer = getFileJavascript("scripts/javascript/JsWarExplorer.js");
            InputStream jsWarKamikaze = getFileJavascript("scripts/javascript/JsWarKamikaze.js");
            InputStream jsWarRocketLauncher = getFileJavascript("scripts/javascript/JsWarRocketLauncher.js");
            InputStream jsWarTurret = getFileJavascript("scripts/javascript/JsWarTurret.js");
            try {
                jsScriptsFirst.put("ClassExtend", createScript(jsClassExtend));

                jsScriptsSecond.put("JsWarAgent", createScript(jsWarAgent));

                jsScriptsThird.put("JsWarBase", createScript(jsWarBase));
                jsScriptsThird.put("JsWarEngineer", createScript(jsWarEngineer));
                jsScriptsThird.put("JsWarExplorer", createScript(jsWarExplorer));
                jsScriptsThird.put("JsWarKamikaze", createScript(jsWarKamikaze));
                jsScriptsThird.put("JsWarRocketLauncher", createScript(jsWarRocketLauncher));
                jsScriptsThird.put("JsWarTurret", createScript(jsWarTurret));
                jsClassExtend.close();
                jsWarAgent.close();
                jsWarEngineer.close();
                jsWarExplorer.close();
                jsWarKamikaze.close();
                jsWarRocketLauncher.close();
                jsWarTurret.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initConfiguration = true;
        }

        instanciedJsScript(jsScriptsFirst);
        instanciedJsScript(jsScriptsSecond);
        instanciedJsScript(jsScriptsThird);
    }

    private InputStream getFileJavascript(String pathJavascript) {
        return getClass().getClassLoader().getResourceAsStream(pathJavascript);
    }

    @SuppressWarnings("unused")
	private void instanciedJsScript(Map<String, Script> map) {
        Iterator<Entry<String, Script>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Entry<String, Script> thisEntry = entries.next();
                Script s = thisEntry.getValue();

                if (thisEntry.getKey().equals("ClassExtend")) {
                    evalClass(s);
                } else if (thisEntry.getKey().equals("JsWarTools")) {
                    Object o = giveScriptToolsAgent(s, thisEntry.getKey());
                } else {
                    ScriptAgent sa = giveScriptAgent(s, thisEntry.getKey());
                }
            }


    }

    @Override
    public void addScript(InputStream file, WarAgentType agent)
            throws IOException {

        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        addScript(sb, agent);
        scanner.close();

    }

    @Override
    public void addScript(StringBuilder file, WarAgentType agent) {
        JsScript jScript = new JsScript(file);
        scripts.put(agent, jScript);
    }

    @Override
    public void addSCript(Script script, WarAgentType agent) {
        JsScript jsScript = new JsScript(new StringBuilder());
        jsScript.addCodeToAgent(script);
        scripts.put(agent, jsScript);
    }

    @Override
    public Map<WarAgentType, Script> getScripts() {
        return scripts;
    }

    @Override
    public Script getScript(WarAgentType agent) {
        return scripts.get(agent);
    }

    @Override
    public ScriptAgent giveScriptAgent(WarAgentType agent) {
        Script s = scripts.get(agent);
        try {
            engine.eval(s.getCodeAgent().toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        Object o = engine.get(agent.toString());
        Invocable inv = (Invocable) engine;
        ScriptAgent sa = inv.getInterface(o, ScriptAgent.class);
        return sa;
    }

    @Override
    public Script createScript(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        scanner.close();

        Script script = new JsScript(sb);
        return script;
    }

    @Override
    public ScriptAgent giveScriptAgent(Script script, String agentName) {

        try {
            engine.eval(script.getCodeAgent().toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        Object o = engine.get(agentName.toString());
        Invocable inv = (Invocable) engine;
        ScriptAgent sa = inv.getInterface(o, ScriptAgent.class);
        //inv.invokeMethod(sa, "link", new JsWrapper<Toto>(new Toto()))
        return sa;
    }

    public void evalClass(Script script) {
        try {
            engine.eval(script.getCodeAgent().toString());

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object giveScriptToolsAgent(Script script, String toolName) {
        try {
            engine.eval(script.getCodeAgent().toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        Object o = engine.get(toolName.toString());
        Invocable inv = (Invocable) engine;
        Object sa = inv.getInterface(o, Object.class);
        return sa;
    }

    @Override
    public Script createScript(InputStream file) throws IOException {
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        Script script = new JsScript(sb);
        scanner.close();
        return script;
    }

}
