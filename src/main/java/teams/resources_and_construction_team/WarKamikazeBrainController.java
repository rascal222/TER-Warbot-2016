package teams.resources_and_construction_team;

import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarKamikazeBrain;

import java.util.List;

public abstract class WarKamikazeBrainController extends WarKamikazeBrain
{
    public WarKamikazeBrainController()
    {
        super();
    }

    @Override
    public String action()
    {
        List<WarAgentPercept> percepts = getPercepts();

        for (WarAgentPercept p : percepts)
        {
            switch (p.getType())
            {
                case WarBase:
                    if (isEnemy(p))
                        return fire();
                    break;
                default:
                    break;
            }
        }

        if (isBlocked())
            setRandomHeading();
        return move();
    }
}
