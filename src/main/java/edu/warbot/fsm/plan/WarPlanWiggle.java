package edu.warbot.fsm.plan;

import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionWiggle;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

/**
 * Avance de manière perdu
 */
public class WarPlanWiggle<BrainType extends WarBrain & Movable> extends WarPlan<BrainType> {


    private Integer nombreTik;

    public WarPlanWiggle(BrainType brain, GenericPlanSettings planSettings) {
        super("Plan Wiggle", brain, planSettings);

        if (getPlanSettings().Tik_number != null)
            this.nombreTik = getPlanSettings().Tik_number;
        else
            this.nombreTik = 0;
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<BrainType> actionAttaquer = new WarActionWiggle<BrainType>(getBrain(),
                nombreTik);

        addAction(actionAttaquer);

        setFirstAction(actionAttaquer);
    }

}
