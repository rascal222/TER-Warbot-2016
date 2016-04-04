package edu.warbot.game;

import edu.warbot.game.modes.DuelGameMode;
import edu.warbot.game.modes.ResourcesRunGameMode;
import edu.warbot.game.modes.TimerGameMode;
import edu.warbot.game.modes.NumberAgentGameMode;

public enum WarGameMode {

    Duel(DuelGameMode.class),

    ResourcesRun(ResourcesRunGameMode.class),

    TimerRun(TimerGameMode.class),

    NumberAgentGameMode(NumberAgentGameMode.class);

    private Class<? extends WarGame> gameModeClass;

    WarGameMode(Class<? extends WarGame> gameModeClass) {
        this.gameModeClass = gameModeClass;
    }

    public Class<? extends WarGame> getGameModeClass() {
        return gameModeClass;
    }
}
