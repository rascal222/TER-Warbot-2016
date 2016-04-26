package teams.resources_and_construction_team;

import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.agents.WarEngineer;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarBaseBrainController extends WarBaseBrain
{
    public WarBaseBrainController()
    {
        super();
    }

    @Override
    public String action()
    {
    	if ((getHealth() < WarBase.MAX_HEALTH) && (!isBagEmpty()))
            return eat();

        if ((getHealth() > WarEngineer.COST) && (Math.random() < 0.005))
        {
            setNextAgentToCreate(WarAgentType.WarEngineer);
            return create();
        }
        
    	List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs)
        	if (msg.getMessage().equals("Give me your ID base"))
                reply(msg, "I am the base and here is my ID", Integer.toString(getID()));
        
        return idle();
    }

}
