package edu.warbot.fsm;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.condition.WarCondition;
import edu.warbot.fsm.plan.WarPlan;

import java.util.ArrayList;

public class WarEtat<BrainType extends WarBrain> {

    private WarPlan<? extends BrainType> plan;
    private ArrayList<WarCondition<BrainType>> conditions = new ArrayList<>();

    private String nom;

    public WarEtat(String nom, WarPlan<? extends BrainType> plan) {
        this.nom = nom;
        this.plan = plan;
    }

    public void addCondition(WarCondition<BrainType> cond1) {
        this.conditions.add(cond1);
    }

    public WarPlan<? extends BrainType> getPlan() {
        return this.plan;
    }

    public ArrayList<WarCondition<BrainType>> getConditions() {
        return this.conditions;
    }

    public void initEtat() {

        if (this.plan == null) {
            System.err.println("WarEtat : ERREUR : un état doit obligatoirement contenir un plan <" + this.nom + ">");
        }

        if (this.conditions.size() < 1)
            System.out.println("ATTENTION l'état <" + this.nom + "> ne contient aucune conditions de sortie");

        for (WarCondition<BrainType> cond : this.conditions) {
            cond.init();
        }

        System.out.println("\tL'état <" + this.getName() + "> " + "contient le plan <" + this.getPlan().getNom() + ">" + " et " + this.conditions.size() + " conditions de sorties");

        this.plan.initPlan();

    }

    public String getName() {
        return this.nom;
    }

    public void stateWillBegin() {
        //On dit l'état qu'il va commencer
        this.plan.planWillBegin();
        //On dit aux conditions qu'elles vont commencer
        for (WarCondition<BrainType> warCondition : conditions) {
            warCondition.conditionWillBegin();
        }
    }

}
