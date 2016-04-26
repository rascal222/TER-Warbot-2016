package edu.warbot.game;

import edu.warbot.game.modes.DuelGameMode;
import edu.warbot.game.modes.RelicGatheringGameMode;
//import edu.warbot.game.modes.ResourcesRunGameMode;
//import edu.warbot.game.modes.TimerGameMode;
//import edu.warbot.game.modes.NumberAgentGameMode;
import edu.warbot.game.modes.ResourcesAndConstructionMode;

import edu.warbot.gui.launcher.WarGameModePanel;
import edu.warbot.gui.launcher.DuelPanel;
import edu.warbot.gui.launcher.RelicGatheringPanel;
import edu.warbot.gui.launcher.ResourcesAndConstructionPanel;


public enum WarGameMode {

    Duel(DuelGameMode.class, DuelPanel.class),
    
    RelicGathering(RelicGatheringGameMode.class, RelicGatheringPanel.class),

    RessourcesAndConstruction(ResourcesAndConstructionMode.class, ResourcesAndConstructionPanel.class);

//    ResourcesRun(ResourcesRunGameMode.class),

//    TimerRun(TimerGameMode.class),

//    NumberAgentGameMode(NumberAgentGameMode.class);

    private Class<? extends WarGame> gameModeClass;
    private Class<? extends WarGameModePanel> gameModePanelClass;

    WarGameMode(Class<? extends WarGame> gameModeClass, Class<? extends WarGameModePanel> gameModePanelClass) {
        this.gameModeClass = gameModeClass;
        this.gameModePanelClass = gameModePanelClass;
    }

    public Class<? extends WarGame> getGameModeClass() {
        return gameModeClass;
    }
    
    public Class<? extends WarGameModePanel> getGameModePanelClass() {
        return gameModePanelClass;
    }
}
