package edu.warbot.game.modes.endCondition;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.TeamListener;

import java.util.ArrayList;
import java.util.List;

public class NumberAgentEndCondition extends AbstractEndCondition implements TeamListener {

    @SuppressWarnings("unused")
	private List<WarNumberAgent> agents;

    private List<WarCollectionNumberAgentByTeam> collectionAgentByTeam;

    public NumberAgentEndCondition(WarGame game) {
        super(game);
        this.agents = new ArrayList<WarNumberAgent>();
        this.collectionAgentByTeam = new ArrayList<WarCollectionNumberAgentByTeam>();


    }

    @Override
    public void doAfterEachTick() {


    }

    @Override
    public boolean isGameEnded() {

//		// Si pas de condition sur le nombre d'agent
//		if(agents.size() == 0)
//			return false;
//		
//		Iterator<Entry<WarAgentType, Long>> entries = currentsAgents.entrySet().iterator();
//		
//		while (entries.hasNext()) {
//			Entry<WarAgentType, Long> thisEntry = (Entry<WarAgentType, Long>) entries.next();
//			
//			WarAgentType agent = thisEntry.getKey();
//			Long numberAgent = thisEntry.getValue();
//			
//			// Si l'agent à une condition sur son nombre
//			if(agents.contains(agent)) {			
//				WarNumberAgent condAgent = getConditionAgent(agent);
//	
//				if((numberAgent > condAgent.getMax()) || (numberAgent < condAgent.getMin()))
//						return true;
//			}
//		}

        return false;
    }

    @Override
    public void onAgentAdded(WarAgent newAgent) {
        for (WarCollectionNumberAgentByTeam collection : collectionAgentByTeam) {
            if (newAgent.getTeam().equals(collection.getInGameTeam())) {
                if (collection.isInCollectionOfAgent(newAgent)) {
                    collection.incrementNumberOfAgent(newAgent);
                } else {
                    collection.addNewAgent(newAgent);
                }
            }
        }
    }

    @Override
    public void onAgentRemoved(WarAgent removedAgent) {
        for (WarCollectionNumberAgentByTeam collection : collectionAgentByTeam) {
            if (removedAgent.getTeam().equals(collection.getInGameTeam())) {
                if (collection.isInCollectionOfAgent(removedAgent)) {
                    collection.decrementNumberOfAgent(removedAgent);
                }
            }
        }
    }

    public void setAgents(List<WarNumberAgent> agents) {
        this.agents = agents;
    }

    public WarNumberAgent getConditionAgent(WarAgentType agent) {
//		for(WarNumberAgent agentCond : agents) {
//			if(agentCond.getAgent().equals(agent))
//				return agentCond;
//		}

        return null;
    }

    public boolean containsWarAgent(WarAgent agent) {
//		Iterator<Entry<WarAgentType, Long>> entries = currentsAgents.entrySet().iterator();
//		
//		while (entries.hasNext()) {
//			Entry<WarAgentType, Long> thisEntry = (Entry<WarAgentType, Long>) entries.next();
//			
//			if(thisEntry.getKey().equals(agent))
//					return true;
//		}

        return false;
    }

}