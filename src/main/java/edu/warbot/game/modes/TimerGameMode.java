package edu.warbot.game.modes;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.TimerEndCondition;

public class TimerGameMode extends AbstractGameMode {

    public TimerGameMode(WarGameSettings settings, WarGame game, Object[] arg) {
        super(settings);
        this.setEndCondition(new TimerEndCondition(game, (Long) arg[0]));
    }

	@Override
	protected void launchAllAgentsForThisGameMode() {
		// TODO Auto-generated method stub
		
	}

}