package teams.resources_and_construction_team;

import edu.warbot.agents.agents.WarEngineer;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarEngineerBrain;
import edu.warbot.brains.capacities.Building;

import java.util.List;

public abstract class WarEngineerBrainController extends WarEngineerBrain
{
    private int nbMove;

    public WarEngineerBrainController()
    {
        super();
        nbMove = 0;
    }

    @Override
    public String action()
    {
    	if (getHealth() <= WarEngineer.MAX_HEALTH && !isBagEmpty())
            return eat();
    	
    	List<WarAgentPercept> percepts = getPercepts();
    	
    	for (WarAgentPercept p : percepts)
    	{
            if((p.getType() == WarAgentType.WarTurret || p.getType() == WarAgentType.WarBase) && !isEnemy(p) && p.getHealth() < p.getMaxHealth())
            {
                if(p.getDistance() > Building.MAX_DISTANCE_BUILD)
                {
                	setHeading(p.getAngle());
                	return move();
                }
                else
                {
                	setIdNextBuildingToRepair(p.getID());
                	return repair();
                }
            }
            else if(p.getType() == WarAgentType.WarFood)
            {
            	if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagFull())
            	{
                    setHeading(p.getAngle());
                    return take();
                }
            	else if (!isBagFull())
            	{
                    setHeading(p.getAngle());
                    return move();
            	}
            }
        }
    	
    	if(nbMove == 200)
    	{
    		nbMove = 0;
    		double rand = Math.random();
    		if(rand < 0.15)
    		{
    			setNextBuildingToBuild(WarAgentType.WarBase);
    			return build();
    		}
    		else if(rand < 0.3)
    		{
    			setNextBuildingToBuild(WarAgentType.WarTurret);
    			return build();
    		}
    		else if(rand < 0.4)
    		{
    			setNextBuildingToBuild(WarAgentType.Wall);
    			return build();
    		}
    	}
        
    	if (isBlocked())
            setRandomHeading();
    	nbMove++;
        return move();
    }
}
