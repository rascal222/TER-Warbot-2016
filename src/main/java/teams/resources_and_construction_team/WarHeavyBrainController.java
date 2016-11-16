package teams.resources_and_construction_team;

import edu.warbot.agents.agents.WarHeavy;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarHeavyBrain;

import java.util.List;

public abstract class WarHeavyBrainController extends WarHeavyBrain
{
    public WarHeavyBrainController()
    {
        super();
    }

    @Override
    public String action()
    {
        if (getHealth() <= (WarHeavy.MAX_HEALTH / 5))
            return eat();

        List<WarAgentPercept> percepts = getPercepts();
        for (WarAgentPercept p : percepts)
        {
            switch (p.getType())
            {
                case WarFood:
                    if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagFull())
                    {
                        setHeading(p.getAngle());
                        return take();
                    }
                    else if (!isBagFull())
                        setHeading(p.getAngle());
                    break;
                default:
                    if (isEnemy(p))
                    {
                        setHeading(p.getAngle());
                        if (isReloaded())
                            return fire();
                        else
                            return beginReloadWeapon();
                    }
                    break;
            }
        }

        if (isBlocked())
            setRandomHeading();
        return move();
    }

}
