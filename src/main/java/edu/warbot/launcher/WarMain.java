package edu.warbot.launcher;

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
import edu.warbot.loader.TeamLoader;
import org.apache.commons.cli.*;

import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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



    public WarMain() {
        availableTeams = new HashMap<>();
        settings = new WarGameSettings();

        // On récupère les équipes
        LoadingDialog loadDial = new LoadingDialog("Chargement des équipes...");
        loadDial.setVisible(true);

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
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Aucune équipe n'a été trouvé dans le dossier \"" + TEAMS_DIRECTORY_NAME + "\"",
                    "Aucune équipe", JOptionPane.ERROR_MESSAGE);
        }

        loadDial.dispose();
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
        loadingDialog = new LoadingDialog("Lancement de la simulation...");
        loadingDialog.setVisible(true);
        start();
    }

    public void start() {
        game = new WarGame(settings);
        new WarLauncher(game).executeLauncher();
        game.addWarGameListener(this);
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
        if (launcherInterface != null) {
            launcherInterface.displayGameResults(game);

        }
        else { // Si la simulation a été lancée depuis la ligne de commande
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
            game.stopGame();
        }
    }

    @Override
    public void onGameStopped() {
        game.removeWarGameListener(this);
        for (int i = 0; i < game.getAllTeams().size(); ++i) {
            game.getAllTeams().get(i).removeAllAgents();
        }
        launcherInterface.reloadTeams(false);
        game.getPlayerTeams().clear();
        settings.prepareForNewGame();
        logger.log(Level.INFO, "Reset settings");
        launcherInterface.setVisible(true);
    }

    @Override
    public void onGameStarted() {
        loadingDialog.dispose();
    }


    public void reloadTeams(boolean dialog) {
        List<String> othersTeam = new ArrayList<>();
        for (String key : availableTeams.keySet()) {
            if (availableTeams.get(key) instanceof ScriptedTeam)
                othersTeam.add(key);
        }
        for (String key : othersTeam)
            availableTeams.remove(key);


        LoadingDialog loadDial = new LoadingDialog("Chargement des équipes...");
        loadDial.setVisible(dialog);
        TeamLoader tl = new TeamLoader();
        // On initialise la liste des équipes existantes dans le dossier "teams"
        try {
            availableTeams.putAll(tl.loadAllAvailableTeams(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadDial.dispose();

    }
}
