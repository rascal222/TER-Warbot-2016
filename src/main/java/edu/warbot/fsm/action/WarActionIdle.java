package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.brains.WarBrain;

/**
 * Reste sans bougé pendant un certains nombre de tik
 */
public class WarActionIdle<BrainType extends WarBrain> extends WarAction<BrainType> {

    private int nombreTikMax;
    private int currentNombreTik = 0;

    public WarActionIdle(BrainType brain, int nombrePas) {
        super(brain);
        this.nombreTikMax = nombrePas;
    }

    public String executeAction() {
        currentNombreTik++;

        if (currentNombreTik > nombreTikMax) {
            getAgent().setDebugString(this.getClass().getSimpleName() + " IDLE (finish)");
            return MovableWarAgent.ACTION_IDLE;

        } else {
            getAgent().setDebugString(this.getClass().getSimpleName() + " IDLE");
            return MovableWarAgent.ACTION_IDLE;
        }


    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
        currentNombreTik = 0;
    }

}
