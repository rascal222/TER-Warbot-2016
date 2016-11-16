package edu.warbot.fsm;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.condition.WarCondition;
import edu.warbot.fsm.reflexe.WarReflexe;

import java.util.ArrayList;

public class WarFSM<BrainType extends WarBrain> {

    private WarEtat<BrainType> firstEtat;
    private WarEtat<BrainType> etatCourant;

    private ArrayList<WarEtat<BrainType>> listeEtat = new ArrayList<>();
    private ArrayList<WarReflexe<BrainType>> listeReflexe = new ArrayList<>();


    public void initFSM() {
        System.out.println("*** Initialisation de la fsm ***");
        System.out.println("La fsm contient " + this.listeEtat.size() + " états");

        if (this.listeEtat.size() == 0) {
            System.err.println("ERREUR La fsm doit contenir au moins 1 état !");
        }

        if (firstEtat == null) {
            this.firstEtat = listeEtat.get(0);
            System.out.println("ATTENTION vous devez choisir un état de depart : par defaut le premier état ajouté est choisit comme état de départ <" + this.firstEtat.getName() + ">");
        }

        this.etatCourant = firstEtat;

        for (WarEtat<BrainType> e : this.listeEtat) {
            e.initEtat();
        }

        fsmWillBegin();
    }

    private void fsmWillBegin() {
        this.firstEtat.stateWillBegin();
    }

    public String executeFSM() {
        String actionResultat = null;

        if (this.listeReflexe == null | this.listeEtat == null | this.etatCourant == null) {
            System.err.println("La fsm contient des erreurs, etes vous sur de l'avoir ben initialisé ?");
        }

        //On execute les reflexes
        for (WarReflexe<BrainType> r : this.listeReflexe) {
            actionResultat = r.executeReflexe();
        }

        //On execute le plan courant
        if (actionResultat == null)
            actionResultat = this.etatCourant.getPlan().executePlan();

        //On change d'état si besoin
        ArrayList<WarCondition<BrainType>> conditions = this.etatCourant.getConditions();

        for (WarCondition<BrainType> conditionCourante : conditions) {
            if (conditionCourante.isValide()) {
                this.etatCourant = conditionCourante.getEtatDestination();
                this.etatCourant.stateWillBegin();
                break;
            }
        }

        return actionResultat;

    }

    public void addEtat(WarEtat<BrainType> e) {
        if (this.listeEtat.contains(e))
            System.err.println("ATTENTION l'état <" + e.getName() + "> à déjà ete ajoute à la fsm");
        this.listeEtat.add(e);
    }

    public void setFirstEtat(WarEtat<BrainType> e) {
        this.firstEtat = e;
    }

    public void addReflexe(WarReflexe<BrainType> r) {
        this.listeReflexe.add(r);
    }

    public ArrayList<WarEtat<BrainType>> getAllStates() {
        return this.listeEtat;
    }

}
