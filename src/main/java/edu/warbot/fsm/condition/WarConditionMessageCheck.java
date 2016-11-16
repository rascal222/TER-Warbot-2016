package edu.warbot.fsm.condition;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.fsm.editor.settings.EnumMessage;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;

import javax.swing.*;

public class WarConditionMessageCheck<BrainType extends WarBrain> extends WarCondition<BrainType> {

    WarAgentType agentType;
    EnumMessage message;

    public WarConditionMessageCheck(String name, BrainType brain, GenericConditionSettings conditionSettings) {
        super(name, brain, conditionSettings);

        if (conditionSettings.Message != null)
            this.message = conditionSettings.Message;
        else
            JOptionPane.showMessageDialog(null, "You must chose <Message> for condition <WarConditionMessageCheck>", "Missing attribut", JOptionPane.ERROR_MESSAGE);

        if (conditionSettings.Agent_type != null)
            this.agentType = conditionSettings.Agent_type;
        else
            JOptionPane.showMessageDialog(null, "You must chose <Agent_type> for condition <WarConditionMessageCheck>", "Missing attribut", JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public boolean isValide() {
        for (WarMessage m : getBrain().getMessages()) {
            if (m.getMessage().equals(this.message.name())
                    && m.getSenderType().equals(agentType)) {
                return true;
            }
        }
        return false;
    }

}
