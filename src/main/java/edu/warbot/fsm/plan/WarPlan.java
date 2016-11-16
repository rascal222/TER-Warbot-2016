package edu.warbot.fsm.plan;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.condition.WarCondition;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

import java.util.ArrayList;

public abstract class WarPlan<BrainType extends WarBrain> {

    private WarAction<BrainType> actionCourante;
    private WarAction<BrainType> firstAction;
    private ArrayList<WarAction<BrainType>> actions = new ArrayList<>();

    private String nom;
    private BrainType brain;
    private GenericPlanSettings planSettings;
    private boolean printTrace = false;

    public WarPlan(String nomPlan, BrainType brain, GenericPlanSettings planSettings) {
        this.nom = nomPlan;
        this.brain = brain;
        this.planSettings = planSettings;
    }

    /**
     * Ajoute les actions qui contituent le plan.
     * Les actions doivent etre liées avec des conditions
     */
    public abstract void buildActionList();

    public void initPlan() {

        try {
            this.buildActionList();
        } catch (NullPointerException e) {
            System.err.println("ERROR during initializeFSM of warPlan <" + this.nom + ">, check you have set the right attribut for this plan ");
        }

        if (this.actions.size() < 1) {
            System.err.println("ERREUR le plan <" + this.nom + "> ne contient aucune actions à executer");
        }

        if (this.firstAction == null) {
            this.firstAction = actions.get(0);
            System.out.println("ATTENTION vous devez choisir une action de depart : par defaut la première action ajouter est choisit comme action de départ <" + this.firstAction.getName() + ">");
        }


        this.actionCourante = this.firstAction;

        if (this.getBrain().getDebugString() == "")
            this.getBrain().setDebugString(this.getFirstAction().getName());

        this.printTrace();

    }

    /**
     * TODO : doc
     */
    public String executePlan() {

        String instructionResultat;

        //On execute l'action
        instructionResultat = this.actionCourante.executeAction();

        //On change d'état si besoin
        ArrayList<WarCondition<BrainType>> conditions = this.actionCourante.getConditions();

        for (WarCondition<BrainType> conditionCourante : conditions) {
            if (conditionCourante.isValide()) {
                this.actionCourante = conditionCourante.getActionDestination();
                this.actionCourante.actionWillBegin();
                break;
            }
        }

        if (instructionResultat == null) {
            System.err.println("ERREUR l'instruction renvoyée par <" + this.actionCourante.getName() + "> est vide");
            System.exit(0);
        }

        return instructionResultat;
    }

    public void planWillBegin() {
        firstAction.actionWillBegin();
        actionCourante = firstAction;
    }

    private void printTrace() {
        if (!this.printTrace)
            return;

        System.out.println("\tLe plan contient <" + this.getNom() + "> contient " + this.actions.size() + " actions");

        for (WarAction<BrainType> act : this.actions) {
            System.out.println("\t\tL'action <" + act.getName() + "> contient " + act.getConditions().size() + " conditions de sortie");

            for (WarCondition<BrainType> cond : act.getConditions()) {
                System.out.println("\t\t\tLa condition <" + cond.getClass().getSimpleName() + " a pour destination <" + cond.getActionDestination().getName() + ">");
            }
        }

    }

    public void addAction(WarAction<BrainType> a) {
        this.actions.add(a);
    }

    public BrainType getBrain() {
        return this.brain;
    }

    public String getNom() {
        return this.nom;
    }

    public WarAction<BrainType> getFirstAction() {
        return this.firstAction;
    }

    public void setFirstAction(WarAction<BrainType> a) {
        this.firstAction = a;
    }

    public void setPrintTrace(boolean b) {
        this.printTrace = b;
    }

    public ArrayList<WarAction<BrainType>> getListAction() {
        return this.actions;
    }

    public GenericPlanSettings getPlanSettings() {
        return planSettings;
    }

}
