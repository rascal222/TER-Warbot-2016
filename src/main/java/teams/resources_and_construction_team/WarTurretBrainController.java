package teams.resources_and_construction_team;

import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarTurretBrain;

import java.util.List;

public abstract class WarTurretBrainController extends WarTurretBrain
{
    private int _sight;

    public WarTurretBrainController()
    {
        super();
        _sight = 0;
    }

    @Override
    public String action()
    {
        _sight += 90;
        _sight %= 360;
        setHeading(_sight);

        List<WarAgentPercept> percepts = getPercepts();
        for (WarAgentPercept p : percepts)
        {
        	if (isEnemy(p))
        	{
                setHeading(p.getAngle());
                if (isReloaded())
                    return fire();
                else
                    return beginReloadWeapon();
            }
        }
        return idle();
    }
}
