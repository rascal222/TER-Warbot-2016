package edu.warbot.game.modes;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.NumberLostAgentEndCondition;

public class NumberLostAgentGameMode extends AbstractGameMode {

    public NumberLostAgentGameMode(WarGameSettings settings, WarGame game, Object[] args) {
        super(settings);
        this.setEndCondition(new NumberLostAgentEndCondition(game, (Long) args[0]));
    }

	@Override
	protected void launchAllAgentsForThisGameMode() {
		// TODO Auto-generated method stub
		
	}

}