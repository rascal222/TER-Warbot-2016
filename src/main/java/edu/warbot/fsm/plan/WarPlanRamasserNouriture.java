package edu.warbot.fsm.plan;

import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionChercherNouriture;
import edu.warbot.fsm.action.WarActionRaporterNouriture;
import edu.warbot.fsm.condition.WarCondition;
import edu.warbot.fsm.condition.WarConditionBooleanCheck;
import edu.warbot.fsm.editor.settings.EnumMethod;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

/**
 * Desciption du plan et de ces actions
 * action cercher nouriture
 * Si j'ai finit => raporter la nouriture (ajouter si le sac est plein comme condition)
 * action raporter de la nouriture
 * si j'ai finit => action chercher de la nouriture	(ajouter si le sac est vide comme condition)
 */
public class WarPlanRamasserNouriture<BrainType extends WarBrain & Movable> extends WarPlan<BrainType> {

    public WarPlanRamasserNouriture(BrainType brain, GenericPlanSettings planSettings) {
        super("Plan Rammaser Nouriture", brain, planSettings);
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<BrainType> actionChercheN = new WarActionChercherNouriture<BrainType>(getBrain());
        addAction(actionChercheN);

        WarAction<BrainType> actionRamenerN = new WarActionRaporterNouriture<BrainType>(getBrain());
        addAction(actionRamenerN);

        GenericConditionSettings condSet1 = new GenericConditionSettings();
        condSet1.Methode = EnumMethod.isBagFull;
        WarCondition<BrainType> condStopChercher = new WarConditionBooleanCheck<BrainType>("cond_tO_R", getBrain(), condSet1);
        actionChercheN.addCondition(condStopChercher);
        condStopChercher.setDestination(actionRamenerN);

        GenericConditionSettings condSet2 = new GenericConditionSettings();
        condSet2.Methode = EnumMethod.isBagEmpty;
        WarCondition<BrainType> condStopRamener = new WarConditionBooleanCheck<BrainType>("cond_tO_C", getBrain(), condSet2);
        actionRamenerN.addCondition(condStopRamener);
        condStopRamener.setDestination(actionChercheN);

        setFirstAction(actionChercheN);
    }

}
