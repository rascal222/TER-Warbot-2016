package edu.warbot.agents.percepts;

import edu.warbot.agents.ControllableWarAgent;

public class WarPercept {

    private ControllableWarAgent observer;

    public WarPercept(ControllableWarAgent observer) {
        this.observer = observer;
    }

    protected ControllableWarAgent getObserver() {
        return observer;
    }
}
