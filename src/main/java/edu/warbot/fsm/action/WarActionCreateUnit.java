package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.brains.WarBaseBrain;

/**
 * Crée le nombre d'unité passé en parametre si ma vie est supperieure a la valeur passé en parametre
 * Crée de agent du type passé en parametre
 */
public class WarActionCreateUnit extends WarAction<WarBaseBrain> {

    int minLife;
    WarAgentType agentType;
    int nbToCreate;
    int nbCreate;

    public WarActionCreateUnit(WarBaseBrain brain, WarAgentType agentType, int nb, int minLife) {
        super(brain);
        this.agentType = agentType;
        this.nbToCreate = nb;
        this.minLife = minLife;
    }

    public String executeAction() {

        if (nbCreate == nbToCreate) {
            getAgent().setDebugString("ActionCreateUnit : all unit created");
            return MovableWarAgent.ACTION_IDLE;
        }

        if (!getAgent().isBagEmpty() && getAgent().getHealth() < this.minLife) {
            getAgent().setDebugString("ActionCreateUnit : eat to restor life");
            return WarBase.ACTION_EAT;
        }

        if (getAgent().getHealth() >= minLife) {
            getAgent().setNextAgentToCreate(agentType);
            nbCreate++;
            getAgent().setDebugString("ActionCreateUnit : create " + this.agentType);
            return WarBase.ACTION_CREATE;
        }

        return WarBase.ACTION_IDLE;

    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
        getAgent().setDebugString(getClass().getSimpleName() + " " + agentType.toString());
        this.nbCreate = 0;
    }

}
