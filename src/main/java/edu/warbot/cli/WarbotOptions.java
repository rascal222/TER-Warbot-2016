package edu.warbot.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by beugnon on 22/06/15.
 *
 * WarbotOptions. contient les options utilisables lors d'une utilisation de Warbot par CLI.
 *
 * @author beugnon
 *
 * @Version 1.0
 * @since 3.2.3
 */
@SuppressWarnings("serial")
public class WarbotOptions extends Options {

    public static final String HELP = "help";
    public static final String HELP_SHORTCUT = "h";
    public static final String GAMEMODE = "gamemode";
    public static final String GAMEMODE_SHORTCUT = "gm";
    private Option helpOption = new Option(HELP_SHORTCUT, HELP, false, "print this message");
    private Option gamemodeOption = Option.builder(GAMEMODE_SHORTCUT).
            longOpt(GAMEMODE).argName("mode").hasArg(true).desc("Choose the game mode").build();


    private Option loglevelOption = Option.builder("l").longOpt("log-level")
            .argName("level").hasArg().desc("Define default logging level").build();


    private Option nbAgentOption = Option.builder("nb").longOpt("agent-number")
            .argName("agentType> <number")
            .valueSeparator(' ')
            .numberOfArgs(2)
            .hasArgs()
            .desc("Define the number of agent with a specific type")
            .build();

    private Option teamOption = Option.builder("t").longOpt("team")
            .required()
            .hasArgs()
            .valueSeparator(' ')
            .argName("teamname1> <teamname2> <...")
            .desc("Choose teams to confront")
            .build();

    public WarbotOptions() {
        super();
        addOption(helpOption);
        addOption(gamemodeOption);
        addOption(loglevelOption);
        addOption(nbAgentOption);
        addOption(teamOption);
    }
}
