package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.agents.WarRocketLauncher;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.fsm.editor.settings.EnumMessage;

import java.util.List;

public class WarActionAttaquer extends WarAction<WarRocketLauncherBrain> {

    WarAgentType agentType;

    public WarActionAttaquer(WarRocketLauncherBrain agentAdapter, WarAgentType agentType) {
        super(agentAdapter);

        this.agentType = agentType;
    }

    public String executeAction() {

        if (!getAgent().isReloaded() && !getAgent().isReloading()) {
            getAgent().setDebugString("ActionAttaquer : reloading");
            return WarRocketLauncher.ACTION_RELOAD;
        }

        List<WarAgentPercept> percept = getAgent().getPerceptsEnemiesByType(this.agentType);

        // Je un agentType dans le percept
        if (percept != null && percept.size() > 0) {

            getAgent().setHeading(percept.get(0).getAngle());

            if (getAgent().isReloaded()) {

                getAgent().setDebugString("ActionAttaquer : fire");
                return WarRocketLauncher.ACTION_FIRE;
            } else {
                //placement mieux

                //en attendant
                getAgent().setDebugString("ActionAttaquer : waiting to reaload");
                if (percept.get(0).getDistance() > 10)
                    return WarRocketLauncher.ACTION_MOVE;
                else
                    return WarRocketLauncher.ACTION_IDLE;
            }
        } else { //Si il ny a pas agentType dans le percept

            WarMessage m = getMessage();
            if (m != null) {
                getAgent().setDebugString("ActionAttaquer : seek enemy with message");
                getAgent().setHeading(m.getAngle());
            } else {
                getAgent().setDebugString("ActionAttaquer : seek enemy <" + agentType + ">");
            }

            if (getAgent().isBlocked())
                getAgent().setRandomHeading();
            return MovableWarAgent.ACTION_MOVE;

        }

    }

    private WarMessage getMessage() {
        for (WarMessage m : getAgent().getMessages()) {
            if (m.getMessage().equals(EnumMessage.enemy_base_here.name()) && agentType == WarAgentType.WarBase)
                return m;
            else if (m.getMessage().equals(EnumMessage.enemy_RL_here.name()) && agentType == WarAgentType.WarRocketLauncher)
                return m;
            else if (m.getMessage().equals(EnumMessage.enemy_explorer_here.name()) && agentType == WarAgentType.WarExplorer)
                return m;
        }
        return null;
    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
    }

}
