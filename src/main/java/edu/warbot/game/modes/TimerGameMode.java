package edu.warbot.game.modes;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.TimerEndCondition;

public class TimerGameMode extends WarGame {

    public TimerGameMode(WarGameSettings settings, Object[] arg) {
        super(settings);
        this.setEndCondition(new TimerEndCondition(this, (Long) arg[0]));
    }

	@Override
	protected void launchAllAgentsForThisGameMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doAfterEachTickForThisGameMode() {
		// TODO Auto-generated method stub
		
	}

}