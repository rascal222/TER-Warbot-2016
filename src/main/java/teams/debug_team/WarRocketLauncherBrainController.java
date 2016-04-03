package teams.debug_team;

import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarRocketLauncherBrainController extends WarRocketLauncherBrain
{
    public WarRocketLauncherBrainController()
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
        if(isBlocked())
        	setRandomHeading();
    	return move();
    }
}