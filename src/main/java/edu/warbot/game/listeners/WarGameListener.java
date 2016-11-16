package edu.warbot.game.listeners;

import edu.warbot.game.InGameTeam;

public interface WarGameListener {

    void onNewTeamAdded(InGameTeam newInGameTeam);

    void onTeamLost(InGameTeam removedInGameTeam);

    void onGameOver();

    void onGameStopped();

    void onGameStarted();
    
    void onGamePaused();
    
    void onGameResumed();
}
