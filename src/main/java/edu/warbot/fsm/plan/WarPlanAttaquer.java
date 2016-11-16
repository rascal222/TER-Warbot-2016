package edu.warbot.fsm.plan;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionAttaquer;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

public class WarPlanAttaquer extends WarPlan<WarRocketLauncherBrain> {

    private WarAgentType agentType;

    public WarPlanAttaquer(WarRocketLauncherBrain brain, GenericPlanSettings planSettings) {
        super("Plan Attaquer", brain, planSettings);

        if (getPlanSettings().Agent_type != null)
            this.agentType = getPlanSettings().Agent_type;
        else
            this.agentType = WarAgentType.WarRocketLauncher;
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<WarRocketLauncherBrain> actionAttaquer =
                new WarActionAttaquer(getBrain(), agentType);

        addAction(actionAttaquer);

        setFirstAction(actionAttaquer);
    }

}
