package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.editor.settings.EnumMessage;

public class WarActionSendMessage<BrainType extends WarBrain> extends WarAction<BrainType> {

    EnumMessage message;
    WarAgentType agentType;

    public WarActionSendMessage(BrainType agentAdapter, WarAgentType agentType, EnumMessage message) {
        super(agentAdapter);
        this.agentType = agentType;
        this.message = message;
    }

    public String executeAction() {

        getAgent().broadcastMessageToAgentType(this.agentType, message.name(), "");

        return MovableWarAgent.ACTION_IDLE;

    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
    }

}
