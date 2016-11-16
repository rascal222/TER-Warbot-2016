package edu.warbot.fsm.plan;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionIdle;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

/**
 * Reste sans bougé
 */
public class WarPlanIdle<BrainType extends WarBrain> extends WarPlan<BrainType> {

    private Integer nombreTik;

    public WarPlanIdle(BrainType brain, GenericPlanSettings planSettings) {
        super("Plan Idle", brain, planSettings);

        if (getPlanSettings().Tik_number != null)
            this.nombreTik = getPlanSettings().Tik_number;
        else
            this.nombreTik = 0;
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<BrainType> actionAttaquer = new WarActionIdle<>(getBrain(), nombreTik);

        addAction(actionAttaquer);

        setFirstAction(actionAttaquer);
    }

}
