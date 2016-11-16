package edu.warbot.fsm.action;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.condition.WarCondition;

import java.util.ArrayList;

public abstract class WarAction<BrainType extends WarBrain> {

    ArrayList<WarCondition<BrainType>> conditions = new ArrayList<>();

    private String nom;

    private BrainType agent;

    public WarAction(BrainType b) {
        this.agent = b;
        this.nom = this.getClass().getSimpleName();
    }

    public WarAction(BrainType b, String nom) {
        this.agent = b;
        this.nom = nom;
    }

    /**
     * Méthode appelée à chaque tik où l'action doit être executée
     */
    public abstract String executeAction();

    public BrainType getAgent() {
        return this.agent;
    }


    public String getName() {
        return this.nom;
    }

    public ArrayList<WarCondition<BrainType>> getConditions() {
        return this.conditions;
    }

    public void addCondition(WarCondition<BrainType> cond) {
        this.conditions.add(cond);
    }

    /**
     * Méthode appelée avant chaque première exécution de l'action
     */
    public void actionWillBegin() {
        for (WarCondition<BrainType> warCondition : conditions) {
            warCondition.conditionWillBegin();
        }
        getAgent().setDebugString(this.getClass().getSimpleName());
    }

}
