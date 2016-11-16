package edu.warbot.fsm.condition;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.WarEtat;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;

public abstract class WarCondition<BrainType extends WarBrain> {

    public static final String HEALTH = "getHealth";
    public static final String NB_ELEMEN_IN_BAG = "getNbElementsInBag";
    String name;
    BrainType brain;
    GenericConditionSettings conditionSettings;
    private WarEtat<BrainType> etatDestination;
    private WarAction<BrainType> actionDestination;

    public WarCondition(String name, BrainType brain, GenericConditionSettings conditionSettings) {
        this.name = name;
        this.brain = brain;
        this.conditionSettings = conditionSettings;
    }

    public abstract boolean isValide();

    public void conditionWillBegin() {
        //Ici rien a faire mais certains conditions peuvent en avoir besoin
    }

    public void setDestination(WarEtat<BrainType> etatDestination) {
        this.etatDestination = etatDestination;
    }

    public void setDestination(WarAction<BrainType> a) {
        this.actionDestination = a;
    }

    public WarEtat<BrainType> getEtatDestination() {
        return etatDestination;
    }

    public void init() {
        if (this.etatDestination == null & this.actionDestination == null) {
            System.err.println("ERREUR une condition doit obligatoirement avoir un état ou une action destination <" + this.toString() + ">");
        }

        if (this.etatDestination == null) {
            System.out.println("ATTENTION la condition <" + this.getClass() + "> ne contient pas d'état de sortie. Par default l'action appelée sera celle de l'état courant");
        }


    }

    public WarAction<BrainType> getActionDestination() {
        return this.actionDestination;
    }

    public String getName() {
        return name;
    }

    public BrainType getBrain() {
        return this.brain;
    }

    public GenericConditionSettings getConditionSettings() {
        return conditionSettings;
    }

    public void setConditionSettings(GenericConditionSettings condSettings) {
        this.conditionSettings = condSettings;
    }

}
