package edu.warbot.launcher;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.ScriptedTeam;
import edu.warbot.agents.teams.Team;
import edu.warbot.cli.WarbotOptions;
import edu.warbot.exceptions.WarCommandException;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameMode;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.listeners.WarGameListener;
import edu.warbot.gui.launcher.LoadingDialog;
import edu.warbot.gui.launcher.WarLauncherInterface;
import edu.warbot.gui.viewer.gdx.WarViewerGdx;
import edu.warbot.loader.TeamLoader;

public class WarMain implements WarGameListener {

    public static final String TEAMS_DIRECTORY_NAME = "teams";
    public static final String CMD_NAME = "java WarMain";
    public static final String CMD_HELP = "--help";
    private static final Logger logger = Logger.getLogger(WarMain.class.getCanonicalName());

    private LoadingDialog loadingDialog;

    private WarGame game;
    private WarGameSettings settings;

    private WarLauncherInterface launcherInterface;
    private Map<String, Team> availableTeams;
    
    private WarViewerGdx gdxGame;
    private JFrame gdxFrame;

    public WarMain() {
        WarGame.addWarGameListener(this);
        
        availableTeams = new HashMap<>();
        settings = new WarGameSettings();

        // On récupère les équipes
        loadingDialog = new LoadingDialog("Chargement des équipes...");
        loadingDialog.setVisible(true);

        TeamLoader tl = new TeamLoader();
        
        // On initialise la liste des équipes existantes dans le dossier "teams"
        availableTeams = tl.loadAllAvailableTeams(true);
        // On vérifie qu'au moins une équipe a été chargée
        if (availableTeams.size() > 0) {
            // On lance la launcher interface
            final WarMain warMain = this;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
					launcherInterface = new WarLauncherInterface(warMain, settings);
					launcherInterface.setVisible(true);
                  
					LwjglApplicationConfiguration configCanvas = new LwjglApplicationConfiguration();
					gdxGame = new WarViewerGdx(800, 600);
					LwjglCanvas gameCanvas = new LwjglCanvas(gdxGame, configCanvas);
					gdxFrame = new JFrame("Warbot 2.5D !!");
					gdxFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					gdxFrame.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			            	WarGame.getInstance().setGamePaused();
			            	int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous fermer la fenêtre graphique ?", "Demande de confirmation", JOptionPane.YES_NO_OPTION);
			            	if (confirmation == JOptionPane.YES_OPTION)
								gdxFrame.setExtendedState(JFrame.ICONIFIED);
//			            		gdxFrame.setVisible(false);
			            	WarGame.getInstance().setGameResumed();
			            }
			        });
					gdxFrame.setAlwaysOnTop(true);
					gdxFrame.add(gameCanvas.getCanvas());
					gdxFrame.setSize(800, 600);
					gdxFrame.setMinimumSize(new Dimension(450, 450));
					gdxFrame.setVisible(true);
					gdxFrame.setExtendedState(JFrame.ICONIFIED);
//					while(gdxFrame.isVisible())
//						gdxFrame.setVisible(false);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Aucune équipe n'a été trouvé dans le dossier \"" + TEAMS_DIRECTORY_NAME + "\"",
                    "Aucune équipe", JOptionPane.ERROR_MESSAGE);
        }
        
        loadingDialog.setVisible(false);
    }

    public WarMain(WarGameSettings settings, String... selectedTeamsName) throws WarCommandException {
        availableTeams = new HashMap<>();

        TeamLoader tl = new TeamLoader();
        // On initialise la liste des équipes existantes dans le dossier "teams"
        availableTeams = tl.loadAllAvailableTeams(true);

        // On vérifie qu'au moins une équipe a été chargée
        if (availableTeams.size() > 0) {
            // On lance le jeu
            this.settings = settings;
            if (selectedTeamsName.length > 1) {
                for (String teamName : selectedTeamsName) {
                    if (availableTeams.containsKey(teamName))
                        settings.addSelectedTeam(availableTeams.get(teamName));
                    else
                        throw new WarCommandException("InGameTeam \"" + teamName + "\" does not exists. Available teams are : " + availableTeams.keySet());
                }
            } else {
                throw new WarCommandException("Please select at least two teams. Available teams are : " + availableTeams.keySet());
            }
            start();
        } else {
            throw new WarCommandException("Not team found in folder \"" + TEAMS_DIRECTORY_NAME + "\"");
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            //Lancement classique
            new WarMain();
        } else {
            try {
                logger.log(Level.FINE, "Command arguments = " + Arrays.asList(args));
                commandLine(args);
            } catch (WarCommandException e) {
                logger.log(Level.SEVERE, "WarCommand error", e);
            }
        }
    }

    private static void commandLine(String[] args) throws WarCommandException {

        WarGameSettings settings = new WarGameSettings();
        ArrayList<String> selectedTeams = new ArrayList<>();

        WarbotOptions wo = new WarbotOptions();

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine line = parser.parse(wo, args);
            if (line.hasOption(WarbotOptions.HELP)) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("warbot", wo);
                return;
            }
            if (line.hasOption(WarbotOptions.GAMEMODE)) {
                try {
                    logger.info("Setting mode in : " + line.getOptionValue(WarbotOptions.GAMEMODE));
                    WarGameMode gameMode = WarGameMode.valueOf(line.getOptionValue(WarbotOptions.GAMEMODE));
                    settings.setGameMode(gameMode);
                } catch (IllegalArgumentException e) {
                    throw new WarCommandException("Unknown game mode : " + line.getOptionValue(WarbotOptions.GAMEMODE));
                }
            }
            if (line.hasOption("l")) {
                try {
                    settings.setDefaultLogLevel(Level.parse(line.getOptionValue("l")));
                } catch (IllegalArgumentException e) {
                    throw new WarCommandException("Invalid log level : " + line.getOptionValue("l"));
                }
            }

            if (line.hasOption("nb")) {

                String[] values = line.getOptionValues("nb");
                for (int i = 0; i < values.length; i += 2) {
                    logger.info(values[i].toString() + ":" + values[i + 1].toString());
                    try {
                        WarAgentType wat = WarAgentType.valueOf(values[i]);
                        int nb = Integer.parseInt(values[i + 1]);
                        settings.setNbAgentOfType(wat, nb);
                    } catch (IllegalArgumentException e) {
                        throw new WarCommandException("Error when parsing " + values);
                    }
                }
            }

            if (line.hasOption("t")) {
                String[] values = line.getOptionValues("t");
                for (int i = 0; i < values.length; ++i) {
                    selectedTeams.add(values[i]);
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        new WarMain(settings, selectedTeams.toArray(new String[]{}));
    }

    public void startGame() {
        loadingDialog.setMessage("Lancement de la simulation...");
        loadingDialog.setVisible(true);
        start();
    }

    public void start() {
        game = WarGame.createGameFromSettings(settings);
        WarLauncher launcher = new WarLauncher(game);
        launcher.executeLauncher();
    }

    public Map<String, Team> getAvailableTeams() {
        return (availableTeams);
    }

    @Override
    public void onNewTeamAdded(InGameTeam newTeam) {

    }

    @Override
    public void onTeamLost(InGameTeam removedTeam) {

    }

    @Override
    public void onGameOver() {
        if (launcherInterface == null)
        { // Si la simulation a été lancée depuis la ligne de commande
            String finalTeams = "";
            for (InGameTeam team : game.getPlayerTeams()) {
                finalTeams += team.getName() + ", ";
            }
            finalTeams = finalTeams.substring(0, finalTeams.length() - 2);
            if (game.getPlayerTeams().size() == 1) {
                logger.log(Level.INFO, "Victoire de : " + finalTeams);
            } else {
                logger.log(Level.INFO, "Ex-Aequo entre les équipes : " + finalTeams);
            }
            game.setGameStopped();
        }
    }

    @Override
    public void onGameStopped() {
		gdxFrame.setExtendedState(JFrame.ICONIFIED);
//    	gdxFrame.setVisible(false);
        for (int i = 0; i < game.getAllTeams().size(); ++i) {
            game.getAllTeams().get(i).removeAllAgents();
        }
        game.getPlayerTeams().clear();
        settings.prepareForNewGame();
        logger.log(Level.INFO, "Reset settings");
        launcherInterface.setVisible(true);
        launcherInterface.revalidate();
        launcherInterface.repaint();
    }

    @Override
    public void onGameStarted() {
        loadingDialog.setVisible(false);
        if(settings.isEnabledEnhancedGraphism())
			gdxFrame.setExtendedState(JFrame.NORMAL);
//        	gdxFrame.setVisible(true);
    }

    public void reloadTeams() {
    	loadingDialog = new LoadingDialog("Chargement des équipes...");
    	loadingDialog.setVisible(true);
        List<String> othersTeam = new ArrayList<>();
        for (String key : availableTeams.keySet()) {
            if (availableTeams.get(key) instanceof ScriptedTeam)
                othersTeam.add(key);
        }
        for (String key : othersTeam)
            availableTeams.remove(key);
        
        TeamLoader tl = new TeamLoader();
        // On initialise la liste des équipes existantes dans le dossier "teams"
        try {
            availableTeams.putAll(tl.loadAllAvailableTeams(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    	loadingDialog.setVisible(false);
    }

	@Override
	public void onGamePaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameResumed() {
		// TODO Auto-generated method stub
		
	}
}
