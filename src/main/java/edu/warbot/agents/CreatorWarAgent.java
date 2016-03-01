package edu.warbot.agents;

import edu.warbot.agents.actions.CreatorActionsMethods;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Creator;
import edu.warbot.game.InGameTeam;

public abstract class CreatorWarAgent extends ControllableWarAgent implements CreatorActionsMethods, Creator {

    private WarAgentType _nextAgentToCreate;

    public CreatorWarAgent(String firstActionToDo, InGameTeam inGameTeam, Hitbox hitbox, WarBrain brain, double distanceOfView, double angleOfView, int cost, int maxHealth, int bagSize, int armor) {
        super(firstActionToDo, inGameTeam, hitbox, brain, distanceOfView, angleOfView, cost, maxHealth, bagSize, armor);

        _nextAgentToCreate = WarAgentType.WarExplorer;
    }

    @Override
    public String create() {
        getTeam().createUnit(this, _nextAgentToCreate);
        return getBrain().action();
    }

    @Override
    public WarAgentType getNextAgentToCreate() {
        return _nextAgentToCreate;
    }

    @Override
    public void setNextAgentToCreate(WarAgentType nextAgentToCreate) {
        _nextAgentToCreate = nextAgentToCreate;
    }

}
