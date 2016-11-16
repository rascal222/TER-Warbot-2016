package edu.warbot.scriptcore.interpreter.python;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.python.core.PyInstance;
import org.python.core.PyObjectDerived;
import org.python.util.PythonInterpreter;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.scriptcore.exceptions.NotFoundConfigurationException;
import edu.warbot.scriptcore.interpreter.ScriptInterpreter;
import edu.warbot.scriptcore.script.PyScript;
import edu.warbot.scriptcore.script.Script;
import edu.warbot.scriptcore.scriptagent.ScriptAgent;


public class PyScriptInterpreter extends PythonInterpreter implements ScriptInterpreter {

    private static boolean initConfigurationFile = false;
    private static Map<String, Script> pyScriptsFirst = new HashMap<>();
    private static Map<String, Script> pyScriptsSecond = new HashMap<>();
    private static boolean initTemplateTeam = false;
    private static Map<String, Script> team = new HashMap<>();
    protected Map<WarAgentType, Script> scripts = new HashMap<>();

    public PyScriptInterpreter() {
    }

    /**
     * Cette méthode ne peut pas être en static car on a besoin de chemin dynamic de la class
     */
    public void initConfigurationFilePython() {

        if (!initConfigurationFile) {
            String defaultSourceFile = "scripts/python/";

            InputStream pyWarTools = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarTools);
            InputStream pyWarAgent = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarAgent);

            InputStream pyWarBase = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarBase);
            InputStream pyWarEngineer = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarEngineer);
            InputStream pyWarExplorer = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarExplorer);
            InputStream pyWarKamikaze = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarKamikaze);
            InputStream pyWarLight = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarLight);
            InputStream pyWarHeavy = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarHeavy);
            InputStream pyWarRocketLauncher = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarRocketLauncher);
            InputStream pyWarTurret = getFileConfigPython(defaultSourceFile, FileConfigPython.PyWarTurret);

            try {
                pyScriptsFirst.put(FileConfigPython.PyWarAgent.getNameFile(), createScript(pyWarAgent));
                pyScriptsFirst.put(FileConfigPython.PyWarTools.getNameFile(), createScript(pyWarTools));
                pyScriptsSecond.put(FileConfigPython.PyWarBase.getNameFile(), createScript(pyWarBase));
                pyScriptsSecond.put(FileConfigPython.PyWarEngineer.getNameFile(), createScript(pyWarEngineer));
                pyScriptsSecond.put(FileConfigPython.PyWarExplorer.getNameFile(), createScript(pyWarExplorer));
                pyScriptsSecond.put(FileConfigPython.PyWarKamikaze.getNameFile(), createScript(pyWarKamikaze));
                pyScriptsSecond.put(FileConfigPython.PyWarLight.getNameFile(), createScript(pyWarLight));
                pyScriptsSecond.put(FileConfigPython.PyWarHeavy.getNameFile(), createScript(pyWarHeavy));
                pyScriptsSecond.put(FileConfigPython.PyWarRocketLauncher.getNameFile(), createScript(pyWarRocketLauncher));
                pyScriptsSecond.put(FileConfigPython.PyWarTurret.getNameFile(), createScript(pyWarTurret));
                pyWarTools.close();
                pyWarAgent.close();
                pyWarBase.close();
                pyWarEngineer.close();
                pyWarExplorer.close();
                pyWarKamikaze.close();
                pyWarLight.close();
                pyWarHeavy.close();
                pyWarRocketLauncher.close();
                pyWarTurret.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initConfigurationFile = true;
        }
        instanciedPyScript(pyScriptsFirst);
        instanciedPyScript(pyScriptsSecond);
    }


    public void initConfigurationTeamPython() {
        if (!initTemplateTeam) {
            String defaultSourceFile = "team/python/";
            InputStream WarBase = getFileTeamPython(defaultSourceFile, FileTeamPython.WarBase);
            InputStream WarEngineer = getFileTeamPython(defaultSourceFile, FileTeamPython.WarEngineer);
            InputStream WarExplorer = getFileTeamPython(defaultSourceFile, FileTeamPython.WarExplorer);
            InputStream WarKamikaze = getFileTeamPython(defaultSourceFile, FileTeamPython.WarKamikaze);
            InputStream WarLight = getFileTeamPython(defaultSourceFile, FileTeamPython.WarLight);
            InputStream WarHeavy = getFileTeamPython(defaultSourceFile, FileTeamPython.WarHeavy);
            InputStream WarRocketLauncher = getFileTeamPython(defaultSourceFile, FileTeamPython.WarRocketLauncher);
            InputStream WarTurret = getFileTeamPython(defaultSourceFile, FileTeamPython.WarTurret);            

            try {
                team.put(FileTeamPython.WarBase.getNameFile(), createScript(WarBase));
                team.put(FileTeamPython.WarEngineer.getNameFile(), createScript(WarEngineer));
                team.put(FileTeamPython.WarExplorer.getNameFile(), createScript(WarExplorer));
                team.put(FileTeamPython.WarKamikaze.getNameFile(), createScript(WarKamikaze));
                team.put(FileTeamPython.WarLight.getNameFile(), createScript(WarLight));
                team.put(FileTeamPython.WarHeavy.getNameFile(), createScript(WarHeavy));
                team.put(FileTeamPython.WarRocketLauncher.getNameFile(), createScript(WarRocketLauncher));
                team.put(FileTeamPython.WarTurret.getNameFile(), createScript(WarTurret));
                WarBase.close();
                WarEngineer.close();
                WarExplorer.close();
                WarKamikaze.close();
                WarLight.close();
                WarHeavy.close();
                WarRocketLauncher.close();
                WarTurret.close();                
            } catch (IOException e) {
                e.printStackTrace();
            }
            initTemplateTeam = true;
        }
    }

    private InputStream getFileConfigPython(String pathPython, FileConfigPython nameFilePython) {
        InputStream path = null;
//    	URL path = null;
        try {
//        	  path = getClass().getClassLoader().getResource(pathPython+nameFilePython);
            path = getClass().getClassLoader().getResourceAsStream(pathPython + nameFilePython);

            if (path == null)
                throw new NotFoundConfigurationException(nameFilePython.getNameFile());

        } catch (NotFoundConfigurationException e) {
            System.err.println("Config file " + e.getMessage());
            e.printStackTrace();
        }

        return path;
    }

    private InputStream getFileTeamPython(String pathPython, FileTeamPython nameFilePython) {
        InputStream path = null;
        try {
            //path = getClass().getClassLoader().getResource(pathPython+nameFilePython);
            path = getClass().getClassLoader().getResourceAsStream(pathPython + nameFilePython);
            
            if (path == null)
                throw new NotFoundConfigurationException(nameFilePython.getNameFile());

        } catch (NotFoundConfigurationException e) {
            System.out.println("InGameTeam file " + e.getMessage());
            e.printStackTrace();
        }

        return path;
    }


    private void instanciedPyScript(Map<String, Script> map) {
        Iterator<Entry<String, Script>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, Script> thisEntry = entries.next();
            Script s = thisEntry.getValue();
            if (thisEntry.getKey().equals(FileConfigPython.PyWarTools.getNameFile()))
                giveScriptToolsAgent(s, thisEntry.getKey());
            else
                giveScriptAgent(s, thisEntry.getKey());
        }
    }

    @SuppressWarnings("unused")
	private PyInstance createClass(final String className) {
        return (PyInstance) eval(className + "()");
    }

    private PyObjectDerived createDerivedClass(final String className) {
        return (PyObjectDerived) eval(className + "()");
    }


    /**
     * Permet de créer un script agent avec le bon code
     *
     * @param agent : le type d'agent qu'on veut créer
     * @return un ScriptAgent
     */

    @Override
    public ScriptAgent giveScriptAgent(WarAgentType agent) {
        Script s = scripts.get(agent);
        Script template = getTemplateAgent(agent);
        template.addCodeToAgent(s);
        exec(template.getCodeAgent().toString());
        PyObjectDerived a = createDerivedClass(agent.toString());
        return (ScriptAgent) a.__tojava__(ScriptAgent.class);
    }

    public Script getTemplateAgent(WarAgentType agent) {
        Iterator<Entry<String, Script>> entries = team.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, Script> thisEntry = entries.next();
            if (thisEntry.getKey().equals(agent.toString()))
                return thisEntry.getValue();

        }
        return null;
    }

    /**
     * Pour gérer les classes que vont hériter les class coder pas user
     *
     * @param script
     * @param agentName
     * @return
     */
    @Override
    public ScriptAgent giveScriptAgent(Script script, String agentName) {
        exec(script.getCodeAgent().toString());
        PyObjectDerived a = createDerivedClass(agentName);
        return (ScriptAgent) a.__tojava__(ScriptAgent.class);
    }

    @Override
    public void addScript(InputStream file, WarAgentType agent) throws IOException {
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
        PyScript pScript = new PyScript(file);
        scripts.put(agent, pScript);
    }

    @Override
    public void addSCript(Script script, WarAgentType agent) {
        PyScript pyScript = new PyScript(new StringBuilder());
        pyScript.addCodeToAgent(script);
        scripts.put(agent, pyScript);
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
    public Script createScript(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        scanner.close();

        return new PyScript(sb);
    }


    @Override
    public Object giveScriptToolsAgent(Script script, String toolName) {
        exec(script.getCodeAgent().toString());
        PyObjectDerived a = createDerivedClass(toolName);
        return a.__tojava__(Object.class);
    }

    @Override
    public Script createScript(InputStream file) throws IOException {

        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            sb.append("\n");
        }
        Script script = new PyScript(sb);
        scanner.close();
        return script;
    }


}
