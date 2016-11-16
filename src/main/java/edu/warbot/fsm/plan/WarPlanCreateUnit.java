package edu.warbot.fsm.plan;

import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionCreateUnit;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

import javax.swing.*;

public class WarPlanCreateUnit extends WarPlan<WarBaseBrain> {

    Integer nombreAgent;
    WarAgentType agentType;

    int minLife;
    boolean pourcentage;

    public WarPlanCreateUnit(WarBaseBrain brain, GenericPlanSettings planSettings) {
        super("Plan create unit", brain, planSettings);

        if (getPlanSettings().Agent_type != null)
            this.agentType = getPlanSettings().Agent_type;
        else
            JOptionPane.showMessageDialog(null, "You must chose <Agent_type> for plan <WarPlanCreateUnit>", "Missing attribut", JOptionPane.ERROR_MESSAGE);

        if (getPlanSettings().Number_agent != null)
            this.nombreAgent = getPlanSettings().Number_agent;
        else
            this.nombreAgent = 1;

        if (getPlanSettings().Pourcentage != null)
            this.pourcentage = getPlanSettings().Pourcentage;
        else
            this.pourcentage = false;

        if (getPlanSettings().Min_life != null)
            this.minLife = getPlanSettings().Min_life;
        else {
            this.minLife = 30;
            this.pourcentage = true;
        }

        if (pourcentage)
            minLife = (int) (WarBase.MAX_HEALTH * minLife / 100);
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<WarBaseBrain> actionC = new WarActionCreateUnit(getBrain(), agentType, nombreAgent, minLife);
        this.addAction(actionC);

        setFirstAction(actionC);
    }

}
