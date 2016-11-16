package edu.warbot.fsm.reflexe;

import edu.warbot.brains.WarBrain;

public abstract class WarReflexe<BrainType extends WarBrain> {

    private BrainType brain;

    private String nom;

    public WarReflexe(BrainType b) {
        this.brain = b;
        this.nom = this.getClass().getSimpleName();
    }

    public WarReflexe(BrainType b, String nom) {
        this.brain = b;
        this.nom = nom;
    }

    public abstract String executeReflexe();

    public BrainType getBrain() {
        return this.brain;
    }

    public String getNom() {
        return this.nom;
    }

}
