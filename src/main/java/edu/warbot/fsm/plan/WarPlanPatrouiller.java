package edu.warbot.fsm.plan;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.action.WarActionAttaquer;
import edu.warbot.fsm.action.WarActionChercherBase;
import edu.warbot.fsm.condition.WarCondition;
import edu.warbot.fsm.condition.WarConditionPerceptCounter;
import edu.warbot.fsm.editor.settings.EnumOperand;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;

import javax.swing.*;

/**
 * Plan avancé ne marche pas encore !
 * Pattrouiller de manière deffensive va chercher la base enemie
 * Pattrouiller de manière offensive va chercher la base enemie et attaquer les enemies
 * que l'agent croise en chemin si il est en position de force
 * (Etre en position de force siginifie que le nombre d'allié/enemie aux allentour est avantageux
 */
public class WarPlanPatrouiller extends WarPlan<WarRocketLauncherBrain> {

    boolean offensif;

    public WarPlanPatrouiller(WarRocketLauncherBrain brain, GenericPlanSettings planSettings) {
        super("Plan Patrouiller", brain, planSettings);
        JOptionPane.showMessageDialog(null, "Attention le plan Patrouillé n'est pas terminé et risque de ne pas fonctionner", "Waring not terminated plan", JOptionPane.INFORMATION_MESSAGE);

        if (getPlanSettings().Offensif != null)
            this.offensif = getPlanSettings().Offensif;
        else
            offensif = false;
    }

    public void buildActionList() {

        setPrintTrace(true);

        WarAction<WarRocketLauncherBrain> actionSeekBase = new WarActionChercherBase(getBrain());
        addAction(actionSeekBase);

        if (this.offensif) {
            WarAction<WarRocketLauncherBrain> actionKillEnemy = new WarActionAttaquer(getBrain(), WarAgentType.WarRocketLauncher);
            addAction(actionKillEnemy);

            GenericConditionSettings condSet1 = new GenericConditionSettings();
            condSet1.Agent_type = WarAgentType.WarRocketLauncher;
            condSet1.Pourcentage = true;
            condSet1.Operateur = EnumOperand.sup;
            condSet1.Reference = 1;
            WarCondition<WarRocketLauncherBrain> condSeekToKill =
                    new WarConditionPerceptCounter<WarRocketLauncherBrain>("cond_kill", getBrain(), condSet1);
            actionSeekBase.addCondition(condSeekToKill);
            condSeekToKill.setDestination(actionKillEnemy);

            GenericConditionSettings condSet2 = new GenericConditionSettings();
            condSet2.Agent_type = WarAgentType.WarRocketLauncher;
            condSet2.Pourcentage = true;
            condSet2.Operateur = EnumOperand.egal;
            condSet2.Reference = 0;
            WarCondition<WarRocketLauncherBrain> condKillToSeek =
                    new WarConditionPerceptCounter<WarRocketLauncherBrain>("cond_seek", getBrain(), condSet2);
            actionKillEnemy.addCondition(condKillToSeek);
            condKillToSeek.setDestination(actionSeekBase);

        }
        setFirstAction(actionSeekBase);
    }

}
