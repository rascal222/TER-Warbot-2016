package edu.warbot.fsm.condition;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.editor.settings.EnumOperand;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class WarConditionPerceptCounter<BrainType extends WarBrain> extends WarCondition<BrainType> {

    WarAgentType agentType;
    EnumOperand operand;
    int reference;

    boolean enemy;

    public WarConditionPerceptCounter(String name, BrainType brain,
                                      GenericConditionSettings conditionSettings) {

        super(name, brain, conditionSettings);

        if (conditionSettings.Agent_type != null)
            this.agentType = conditionSettings.Agent_type;
        else
            JOptionPane.showMessageDialog(null, "You must chose <Agent_type> for condition <WarConditionMessageCheck>", "Missing attribut", JOptionPane.ERROR_MESSAGE);

        if (conditionSettings.Operateur != null)
            this.operand = conditionSettings.Operateur;
        else
            this.operand = EnumOperand.egal;

        if (conditionSettings.Reference != null)
            this.reference = conditionSettings.Reference;
        else
            this.reference = 1;

        if (conditionSettings.Enemie != null)
            this.enemy = conditionSettings.Enemie;
        else
            enemy = true;

    }

    @Override
    public boolean isValide() {

        // Recupere les percepts
        List<WarAgentPercept> percepts = new ArrayList<>();

        if (this.enemy) {
            if (this.agentType == null) {
                percepts = getBrain().getPerceptsEnemies();
            } else {
                percepts = getBrain().getPerceptsEnemiesByType(this.agentType);
            }
        } else {
            if (this.agentType == null) {
                percepts = getBrain().getPerceptsAllies();
            } else {
                percepts = getBrain().getPerceptsAlliesByType(this.agentType);
            }
        }

        if (percepts == null || percepts.size() == 0)
            return false;

        // Fait les verifications
        int nbPercept = percepts.size();

        switch (this.operand) {
            case inf:
                return nbPercept < this.reference;
            case sup:
                return nbPercept > this.reference;
            case egal:
                return nbPercept == this.reference;
            case dif:
                return nbPercept != this.reference;
            default:
                System.err.println("WarConditionPerceptCounter : unknown operateur " + this.operand + this.getClass());
                return false;
        }


    }

}
