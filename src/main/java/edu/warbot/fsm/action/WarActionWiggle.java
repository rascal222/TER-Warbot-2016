package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;

import java.util.Random;

/**
 * Description de l'action : wiggle pendant un certains nombre de pas
 */
public class WarActionWiggle<BrainType extends WarBrain & Movable> extends WarAction<BrainType> {

    private int nbPasMax;
    private int nbPas = 0;

    public WarActionWiggle(BrainType brain, int nombrePas) {
        super(brain);
        this.nbPasMax = nombrePas;
    }

    public String executeAction() {

        nbPas++;

        if (this.nbPas > nbPasMax) {
            getAgent().setDebugString(this.getClass().getSimpleName() + " IDLE (finish)");
            return MovableWarAgent.ACTION_IDLE;

        } else {

            double angle = getAgent().getHeading();

            angle = angle + new Random().nextInt(30) - new Random().nextInt(30);

            getAgent().setHeading(angle);

            if (getAgent().isBlocked())
                getAgent().setRandomHeading();

            getAgent().setDebugString(this.getClass().getSimpleName() + " MOVE");
            return MovableWarAgent.ACTION_MOVE;
        }
    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
        nbPas = 0;
    }

}
