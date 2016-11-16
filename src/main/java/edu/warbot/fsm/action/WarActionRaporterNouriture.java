package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.communications.WarMessage;
import edu.warbot.fsm.editor.settings.EnumMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Raporte la nouriture
 */
public class WarActionRaporterNouriture<BrainType extends WarBrain & Movable> extends WarAction<BrainType> {

    public WarActionRaporterNouriture(BrainType brain) {
        super(brain);
    }

    public String executeAction() {

        if (getAgent().isBagEmpty()) {
            getAgent().setDebugString("Action RapporterNourriture : bag empty");
            if (getAgent().isBlocked())
                getAgent().setRandomHeading();
            return MovableWarAgent.ACTION_MOVE;
        }

        if (getAgent().isBlocked())
            getAgent().setRandomHeading();

        List<WarAgentPercept> percepts = getAgent().getPercepts();

        List<WarAgentPercept> basePercepts = new ArrayList<>();

        for (WarAgentPercept p : percepts) {
            if (p.getType().equals(WarAgentType.WarBase) & p.getTeamName().equals(getAgent().getTeamName()))
                basePercepts.add(p);
        }

        //Si je vois la base
        if (basePercepts != null & basePercepts.size() > 0) {

            getAgent().setDebugString("Action RapporterNourriture : base found");

            WarAgentPercept base = basePercepts.get(0);

            if (base.getDistance() > MovableWarAgent.MAX_DISTANCE_GIVE) {
                getAgent().setHeading(base.getAngle());
                return MovableWarAgent.ACTION_MOVE;
            } else {
                getAgent().setIdNextAgentToGive(base.getID());
                return MovableWarAgent.ACTION_GIVE;
            }

        } else {

            getAgent().setDebugString("Action RapporterNourriture : seek base");

            WarMessage m = this.getMessageFromBase();
            //Si j'ai un message de la base je vais vers elle
            if (m != null) {
                getAgent().setHeading(m.getAngle());
                getAgent().setDebugString("Action RapporterNourriture : msg from base");
                return MovableWarAgent.ACTION_MOVE;
            }

            return MovableWarAgent.ACTION_MOVE;
        }
    }

    private WarMessage getMessageFromBase() {
        for (WarMessage m : getAgent().getMessages()) {
            if (m.getSenderType().equals(WarAgentType.WarBase))
                return m;
        }

        getAgent().broadcastMessageToAgentType(WarAgentType.WarBase, EnumMessage.where_is_base.name(), "");
        return null;
    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
    }
}
