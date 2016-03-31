package edu.warbot.game.modes;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.NumberAgentEndCondition;
import edu.warbot.game.modes.endCondition.WarNumberAgent;

import java.util.ArrayList;
import java.util.List;

public class NumberAgentGameMode extends AbstractGameMode {

    public NumberAgentGameMode(WarGameSettings settings, WarGame game, Object[] args) {
        super(settings);
        this.setEndCondition(new NumberAgentEndCondition(game));

        List<WarNumberAgent> agents = new ArrayList<WarNumberAgent>();

        for (Object agent : args)
            agents.add((WarNumberAgent) agent);

        ((NumberAgentEndCondition) this.getEndCondition()).setAgents(agents);
    }

	@Override
	protected void launchAllAgentsForThisGameMode() {
		// TODO Auto-generated method stub
		
	}

}