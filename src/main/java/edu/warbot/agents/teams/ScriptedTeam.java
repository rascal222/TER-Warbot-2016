package edu.warbot.agents.teams;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.DyingBrain;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.implementations.AgentBrainImplementer;
import edu.warbot.brains.implementations.WarBrainImplementation;
import edu.warbot.game.InGameTeam;
import edu.warbot.loader.ImplementationProducer;
import edu.warbot.scriptcore.exceptions.NotFoundConfigurationException;
import edu.warbot.scriptcore.interpreter.ScriptInterpreter;
import edu.warbot.scriptcore.interpreter.ScriptInterpreterFactory;
import edu.warbot.scriptcore.interpreter.ScriptInterpreterLanguage;
import edu.warbot.scriptcore.script.Script;
import edu.warbot.scriptcore.scriptagent.ScriptAgent;
import edu.warbot.scriptcore.team.*;
import edu.warbot.tools.StringOutputStream;
import javassist.*;
import teams.fsm.WarExplorerBrainController;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

/**
 * @author BEUGNON Sébastien, LOPEZ Jimmy
 */
@SuppressWarnings("unused")
public class ScriptedTeam extends JavaTeam {

    private static Map<WarAgentType, Class<? extends WarBrain>> brains;

    private static List<String> functions;

    public static ClassPool cp = ClassPool.getDefault();

    static {
        //Prépare un ClassPool fonctionnel
        cp.insertClassPath(new ClassClassPath(WarBrainImplementation.class));
        cp.appendClassPath(new ClassClassPath(AgentBrainImplementer.class));
        cp.find(AgentBrainImplementer.class.getCanonicalName());
        try
        {
            WarExplorerBrainController.class.getClassLoader().loadClass(WarBrainImplementation.class.getCanonicalName());
            WarExplorerBrainController.class.getClassLoader().loadClass(AgentBrainImplementer.class.getCanonicalName());
            WarExplorerBrainController.class.getClassLoader().loadClass(WarBrain.class.getCanonicalName());
            CtClass brainImplementationClass = cp.get(WarBrainImplementation.class.getCanonicalName());
        } catch (ClassNotFoundException | NotFoundException e) {
            e.printStackTrace();
        }
        //Prépare l'implémentation standard pour une équipe d'agent scriptée
        ImplementationProducer implementationProducer = new ImplementationProducer(cp);


        brains = new HashMap<>();
        try {
            //Implémentation standard
            brains.put(WarAgentType.WarExplorer,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarExplorer.class));
            brains.put(WarAgentType.WarLight,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarLight.class));
            brains.put(WarAgentType.WarHeavy,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarHeavy.class));
            brains.put(WarAgentType.WarRocketLauncher,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarRocketLauncher.class));
            brains.put(WarAgentType.WarEngineer,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarEngineer.class));
            brains.put(WarAgentType.WarBase,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarBase.class));
            brains.put(WarAgentType.WarKamikaze,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarKamikaze.class));
            brains.put(WarAgentType.WarTurret,
                    implementationProducer.createWarBrainImplementationClass(ScriptableWarTurret.class));
            

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ScriptInterpreter interpreter;

    private boolean initFunction = false;

    private ScriptInterpreterLanguage language;

    private Map<WarAgentType, Script> brainScripts = new HashMap<>();



    /**
     * @param teamName    Le nom d'une équipe
     * @param description La description d'une équipe
     * @param imageIcon   Le logo d'une équipe
     */
    public ScriptedTeam(String teamName, String description, ImageIcon imageIcon) {
        super(teamName, description, imageIcon, brains);
    }

    public static List<String> getFunctions() {
        return functions;
    }

    public ScriptInterpreter getInterpreter() {
        return interpreter;
    }


    @Override
    public ControllableWarAgent instantiateControllableWarAgent(InGameTeam inGameTeam, WarAgentType warAgentType)
            throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        ControllableWarAgent a = super.instantiateControllableWarAgent(inGameTeam, warAgentType);
        return a;
    }

    @Override
    public void associateBrain(ControllableWarAgent a) {

        ScriptInterpreter si = ScriptInterpreterFactory.getInstance(getLanguage()).createScriptInterpreter();
        try {
            si.addSCript(brainScripts.get(a.getType()), a.getType());
            ScriptAgent sa = si.giveScriptAgent(a.getType());
            ((Scriptable) a.getBrain()).setScriptAgent(sa);
            sa.link(a.getBrain());
        } catch (Exception e) {
            StringOutputStream out = new StringOutputStream();
            e.printStackTrace(new PrintStream(out));
            WarBrainImplementation brain = new DyingBrain("Erreur à la compilation\n : ".concat(out.getString()));
            e.printStackTrace();
            try {
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            a.setBrain(brain);
            brain.setAgent(a);
        }
    }

    public void initFunctionList() {

        if (!initFunction) {

            String defaultSourceFile = "scripts/function/";
            String defaultNameFile = "function.txt";

            InputStream file = null;

            functions = new ArrayList<>();
            try {
                URL path = getClass().getClassLoader().getResource(defaultSourceFile + defaultNameFile);
                if (path == null)
                    throw new NotFoundConfigurationException(defaultNameFile);

                file = getClass().getClassLoader().getResourceAsStream(defaultSourceFile + defaultNameFile);

                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    functions.add(scanner.nextLine());
                }
                scanner.close();
            } catch (NotFoundConfigurationException e) {
                System.err.println(e.getMessage());
            }

            initFunction = true;
        }
    }

    @Override
    public Team duplicate(String newName) {
        ScriptedTeam st = new ScriptedTeam(newName, getDescription(), getLogo());
        st.setLanguage(getLanguage());
        st.brainScripts.putAll(brainScripts);
        return st;
    }


    public ScriptInterpreterLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ScriptInterpreterLanguage language) {
        this.language = language;
    }


    public void addBrainScript(InputStream input, WarAgentType warAgentType) {
        try {
            ScriptInterpreter si = ScriptInterpreterFactory.getInstance(getLanguage()).createScriptInterpreter();
            Script script = si.createScript(input);
            brainScripts.put(warAgentType, script);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
