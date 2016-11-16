package edu.warbot.game.modes.endCondition;

import edu.warbot.game.WarGame;

public class TimerEndCondition extends AbstractEndCondition {

    private final double lastTick;
    protected long curentTick;

    public TimerEndCondition(WarGame game, long lastTick) {
        super(game);
        this.lastTick = lastTick;
        resetCurentTick();
    }

    @Override
    public void doAfterEachTick() {
        incrementCurentTick();
    }

    @Override
    public boolean isGameEnded() {
        return curentTick >= lastTick;

    }

    public void incrementCurentTick() {
        curentTick++;
    }

    public void resetCurentTick() {
        curentTick = 0;
    }

}