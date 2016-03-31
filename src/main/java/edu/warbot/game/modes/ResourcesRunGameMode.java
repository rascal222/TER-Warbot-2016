package edu.warbot.game.modes;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.ResourcesRunEndCondition;

public class ResourcesRunGameMode extends AbstractGameMode {

    public ResourcesRunGameMode(WarGameSettings settings, WarGame game, Object[] args) {
        super(settings);
        this.setEndCondition(new ResourcesRunEndCondition(game, (Integer) args[0]));
    }

	@Override
	protected void launchAllAgentsForThisGameMode() {
		// TODO Auto-generated method stub
		
	}

}
