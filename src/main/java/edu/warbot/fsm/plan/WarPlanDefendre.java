package edu.warbot.fsm.plan;

import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionDefendre;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

/**
 * Réalise l'action WarActionDefendre
 */
public class WarPlanDefendre extends WarPlan<WarRocketLauncherBrain> {

    public WarPlanDefendre(WarRocketLauncherBrain brain, GenericPlanSettings planSettings) {
        super("Plan Defendre", brain, planSettings);
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<WarRocketLauncherBrain> actionDef = new WarActionDefendre(getBrain());
        addAction(actionDef);

        setFirstAction(actionDef);
    }

}
