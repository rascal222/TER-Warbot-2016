package edu.warbot.fsm.action;

import edu.warbot.brains.WarBrain;

public class WarActionHealMe<BrainType extends WarBrain> extends WarAction<BrainType> {

    public WarActionHealMe(BrainType brain) {
        super(brain);
    }

    public String executeAction() {

        if (getAgent().isBagEmpty()) {
            getAgent().setDebugString("ActionHealMe : empty bag");
            return BrainType.ACTION_IDLE;
        }

        // TODO
//		if(! getAgent().isFullLife()){
//			getAgent().setDebugString("ActionHealMe : eat");
//			return MovableWarAgent.ACTION_EAT;

//		}else{
//			getAgent().setDebugString("ActionHealMe : full life");
//			return MovableWarAgent.ACTION_IDLE;
//		}
        return BrainType.ACTION_IDLE;
    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
    }
}
