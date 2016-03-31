package edu.warbot.launcher;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.viewer.WarDefaultViewer;
import madkit.action.KernelAction;
import madkit.action.SchedulingAction;
import madkit.kernel.Madkit;
import madkit.message.SchedulingMessage;
import turtlekit.agr.TKOrganization;
import turtlekit.kernel.TKLauncher;
import turtlekit.kernel.TurtleKit;

import java.util.ArrayList;
import java.util.Arrays;

public class WarLauncher extends TKLauncher {

    private final WarGame warGame;

    public WarLauncher(WarGame warGame) {
        super();
        this.warGame = warGame;
    }

    @Override
    protected void activate() {
        super.activate();
        setMadkitProperty("GPU_gradients", "true");
    }

    protected void initProperties() {
        WarGameSettings settings = warGame.getSettings();
        setLogLevel(settings.getLogLevel());
        setMadkitProperty(Madkit.LevelOption.agentLogLevel, settings.getLogLevel().toString());
        setMadkitProperty(Madkit.LevelOption.guiLogLevel, settings.getLogLevel().toString());
        setMadkitProperty(Madkit.LevelOption.kernelLogLevel, settings.getLogLevel().toString());
        setMadkitProperty(Madkit.LevelOption.madkitLogLevel, settings.getLogLevel().toString());
        setMadkitProperty(Madkit.LevelOption.networkLogLevel, settings.getLogLevel().toString());

        setMadkitProperty(TurtleKit.Option.envWidth, String.valueOf(((Double) warGame.getMap().getWidth()).intValue()));
        setMadkitProperty(TurtleKit.Option.envHeight, String.valueOf(((Double) warGame.getMap().getHeight()).intValue()));
        setMadkitProperty(TurtleKit.Option.envDimension, getMadkitProperty(TurtleKit.Option.envWidth) + "," + getMadkitProperty(TurtleKit.Option.envHeight));
        super.initProperties();

        setMadkitProperty(TurtleKit.Option.viewers, "null");
        setMadkitProperty(TurtleKit.Option.scheduler, WarScheduler.class.getName());
        setMadkitProperty(TurtleKit.Option.environment, WarEnvironment.class.getName());
    }

    @Override
    protected void createSimulationInstance() {
//        super.createSimulationInstance();
        launchAgent(this.getMadkitProperty(TurtleKit.Option.environment));

        launchScheduler();

        launchViewers();

        this.launchConfigTurtles();
        
        warGame.launchAllAgents(this);
        /*WarGameSettings settings = warGame.getSettings();
        if (settings.getSituationLoader() == null)
            launchAllAgents();
        else
            settings.getSituationLoader().launchAllAgentsFromSituation(this, warGame);*/

        // Puis on lance la simulation
        sendMessage(getMadkitProperty(turtlekit.kernel.TurtleKit.Option.community),
                TKOrganization.ENGINE_GROUP, TKOrganization.SCHEDULER_ROLE, new SchedulingMessage(SchedulingAction.RUN));


        warGame.setGameStarted();
    }

    @Override
    protected void launchScheduler() {
        WarScheduler scheduler = new WarScheduler(warGame);
        try {
            this.launchAgent(scheduler);
//            scheduler.setSimulationDuration((double) Integer.parseInt(this.getMadkitProperty(TurtleKit.Option.endTime)));
        } catch (NullPointerException | NumberFormatException var3) {
            var3.printStackTrace();
        }
    }

    @Override
    protected void launchViewers() {
        WarDefaultViewer viewer = new WarDefaultViewer(warGame);
        launchAgent(viewer, viewer.isRenderable());
        viewer.moveMapOffsetTo(100, 100);
    }

    public void executeLauncher(String... args) {
        final ArrayList<String> arguments = new ArrayList<>(Arrays.asList(
                Madkit.BooleanOption.desktop.toString(), "false",
                Madkit.Option.configFile.toString(), "turtlekit/kernel/turtlekit.properties"
        ));
        if (args != null) {
            arguments.addAll(Arrays.asList(args));
        }
        new Madkit(arguments.toArray(new String[0])).doAction(KernelAction.LAUNCH_AGENT, this);
    }

    public WarGame getGame() {
        return warGame;
    }
}
