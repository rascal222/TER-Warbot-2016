package edu.warbot.game.modes;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameSettings;
import edu.warbot.game.modes.endCondition.NumberAgentEndCondition;
import edu.warbot.game.modes.endCondition.WarNumberAgent;

import java.util.ArrayList;
import java.util.List;

public class NumberAgentGameMode extends WarGame {

    public NumberAgentGameMode(WarGameSettings settings, Object[] args) {
        super(settings);
        this.setEndCondition(new NumberAgentEndCondition(this));

        List<WarNumberAgent> agents = new ArrayList<WarNumberAgent>();

        for (Object agent : args)
            agents.add((WarNumberAgent) agent);

        ((NumberAgentEndCondition) this.getEndCondition()).setAgents(agents);
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
		return true;
	}

}