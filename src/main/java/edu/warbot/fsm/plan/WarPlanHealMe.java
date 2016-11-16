package edu.warbot.fsm.plan;

/**
 * Me heal
 */

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionHealMe;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

public class WarPlanHealMe<BrainType extends WarBrain> extends WarPlan<BrainType> {

    public WarPlanHealMe(BrainType brain, GenericPlanSettings planSettings) {
        super("Plan heal myself", brain, planSettings);
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<BrainType> actionHeal = new WarActionHealMe<>(getBrain());
        addAction(actionHeal);

        setFirstAction(actionHeal);
    }

}
