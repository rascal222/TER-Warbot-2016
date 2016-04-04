package teams.debug_team;

import edu.warbot.brains.brains.WarTurretBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarTurretBrainController extends WarTurretBrain
{
    public WarTurretBrainController()
    {
        super();
    }

    @Override
    public String action()
    {
    	String messages = "";
        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs)
        {
        	messages += "; " + msg.getSenderID();
        }
        setDebugString(messages);
    	return idle();
    }
}
