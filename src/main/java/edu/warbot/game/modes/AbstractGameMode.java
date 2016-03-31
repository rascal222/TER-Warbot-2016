package edu.warbot.game.modes;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.AbstractEndCondition;

public abstract class AbstractGameMode extends WarGame {

    private AbstractEndCondition endCondition;

    public AbstractGameMode(WarGameSettings settings) {
    	super(settings);
    }

    public AbstractEndCondition getEndCondition() {
        return endCondition;
    }
    
    protected void setEndCondition(AbstractEndCondition endCondition) {
    	this.endCondition = endCondition;
    }
}
