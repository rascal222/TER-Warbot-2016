package edu.warbot.fsm.plan;

import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionFuire;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

import javax.swing.*;

/**
 * A amélioré par exemple en disant que on fuit mais si on a plus de vie on va en chercher et on ce heal
 */

public class WarPlanBeSecure<BrainType extends WarBrain & Movable> extends WarPlan<BrainType> {

    public WarPlanBeSecure(BrainType brain, GenericPlanSettings planSettings) {
        super("PlanBeSecure", brain, planSettings);
        JOptionPane.showMessageDialog(null, "Attention le plan BeSecure n'est pas terminé et risque de ne pas fonctionner", "Waring not terminated plan", JOptionPane.INFORMATION_MESSAGE);
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<BrainType> actionFuire = new WarActionFuire<>(getBrain());
        addAction(actionFuire);

//		WarAction<AgentAdapterType> actionHeal = new WarActionHealMe<>(getBrain());
//		addAction(actionHeal);
//		
//		WarAction<AgentAdapterType> actionFood = new WarActionChercherNouriture<>(getBrain());
//		addAction(actionFood);
//		
//		WarCondition<AgentAdapterType> cond = new WarConditionActionTerminate(getBrain(), actionDontMove);
//		actionDontMove.addCondition(condFinitDontMove);
//		condFinitDontMove.setDestination(actionDontMove);

        setFirstAction(actionFuire);
    }

}
