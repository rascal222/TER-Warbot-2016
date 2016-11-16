package edu.warbot.game.modes.endCondition;

import edu.warbot.agents.enums.WarAgentType;

public class WarNumberAgent {

    private long min;
    private long max;
    private WarAgentType agent;

    public WarNumberAgent(long min, long max, WarAgentType agent) {
        this.min = min;
        this.max = max;
        this.agent = agent;

        try {
            avoidIncoherence();
        } catch (IncoherenceNumberAgentException e) {
            e.printStackTrace();
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public WarAgentType getAgent() {
        return agent;
    }

    public long getMaxValueOfAgent() {
        return Long.MAX_VALUE;
    }

    public long getMinValueOfAgent() {
        return 0;
    }

    public void avoidIncoherence() throws IncoherenceNumberAgentException {
        if ((max < 0) || (min < 0) || (min > max))
            throw new IncoherenceNumberAgentException(this);
    }

}