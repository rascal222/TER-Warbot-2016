package edu.warbot.game.modes.endCondition;

import edu.warbot.game.WarGame;

public abstract class AbstractEndCondition {

    private WarGame game;

    public AbstractEndCondition(WarGame game) {
        this.game = game;
    }

    public abstract void doAfterEachTick();

    public abstract boolean isGameEnded();

    protected WarGame getGame() {
        return game;
    }
}
