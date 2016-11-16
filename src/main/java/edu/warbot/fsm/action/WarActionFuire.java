package edu.warbot.fsm.action;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;

import javax.swing.*;
import java.util.List;

/**
 * Description de l'action
 */

public class WarActionFuire<BrainType extends WarBrain & Movable> extends WarAction<BrainType> {


    public WarActionFuire(BrainType brain) {
        super(brain, "ActionFuire");
        JOptionPane.showMessageDialog(null, "Attention l'action Fuire n'a pas étée vérifiée et risque de ne pas fonctionner", "Waring not terminated action", JOptionPane.INFORMATION_MESSAGE);
    }

    public String executeAction() {

        if (getAgent().isBlocked())
            getAgent().setRandomHeading();

        //Si je n'ai pas denemie autour de moi j'ai terminé
        List<WarAgentPercept> percept = getAgent().getPerceptsEnemiesByType(WarAgentType.WarRocketLauncher);

        // si il y a des enemy je fuis
        if (percept.size() > 0) {
            getAgent().setHeading(percept.get(0).getAngle() + 180);
            return MovableWarAgent.ACTION_MOVE;
        } else {
            // Sinon je prend de la nouriture pour me soigner
            percept = getAgent().getPerceptsResources();
            if (percept.size() > 0) {
                WarAgentPercept p = percept.get(0);
                if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE)
                    return MovableWarAgent.ACTION_TAKE;
                else {
                    getAgent().setHeading(p.getAngle());
                    return MovableWarAgent.ACTION_MOVE;
                }
            } else {
                if (!getAgent().isBagEmpty()) // si j'ai de la nourriture je mange sinon je vais tout droit
                    return MovableWarAgent.ACTION_EAT;
                else
                    return MovableWarAgent.ACTION_MOVE;
            }
        }
    }

    @Override
    public void actionWillBegin() {
        super.actionWillBegin();
    }

}
