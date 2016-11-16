package edu.warbot.brains.implementations;

import edu.warbot.agents.actions.constants.BuilderActions;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.capacities.Builder;

public class WarBuilderBrainImplementation extends WarBrainImplementation implements BuilderActions, Builder {

    @Override
    public WarAgentType getNextBuildingToBuild() {
        return ((Builder) getAgent()).getNextBuildingToBuild();
    }

    @Override
    public void setNextBuildingToBuild(WarAgentType nextBuildingToBuild) {
        ((Builder) getAgent()).setNextBuildingToBuild(nextBuildingToBuild);
    }

    @Override
    public boolean isAbleToBuild(WarAgentType building) {
        return ((Builder) getAgent()).isAbleToBuild(building);
    }

    @Override
    public int getIdNextBuildingToRepair() {
        return ((Builder) getAgent()).getIdNextBuildingToRepair();
    }

    @Override
    public void setIdNextBuildingToRepair(int idNextBuildingToRepair) {
        ((Builder) getAgent()).setIdNextBuildingToRepair(idNextBuildingToRepair);
    }
}
