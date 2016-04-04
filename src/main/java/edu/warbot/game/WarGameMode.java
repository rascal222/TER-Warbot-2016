package edu.warbot.game;

import edu.warbot.game.modes.DuelGameMode;
import edu.warbot.game.modes.RelicGatheringGameMode;
import edu.warbot.game.modes.ResourcesRunGameMode;
import edu.warbot.game.modes.TimerGameMode;
import edu.warbot.game.modes.RessourcesAndConstructionMode;

public enum WarGameMode {

    Duel(DuelGameMode.class),
    
    RelicGathering(RelicGatheringGameMode.class),

    RessourcesAndConstruction(RessourcesAndConstructionMode.class),

    ResourcesRun(ResourcesRunGameMode.class),

    TimerRun(TimerGameMode.class),

    NumberAgentGameMode(edu.warbot.game.modes.NumberAgentGameMode.class);

    private Class<? extends WarGame> gameModeClass;

    WarGameMode(Class<? extends WarGame> gameModeClass) {
        this.gameModeClass = gameModeClass;
    }

    public Class<? extends WarGame> getGameModeClass() {
        return gameModeClass;
    }
}
