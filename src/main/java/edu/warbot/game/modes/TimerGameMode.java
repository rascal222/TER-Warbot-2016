package edu.warbot.game.modes;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
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

	@Override
	public boolean authorizedAgent(InGameTeam inGameTeam, WarAgentType agentType) {
		// TODO Auto-generated method stub
		return true;
	}

}