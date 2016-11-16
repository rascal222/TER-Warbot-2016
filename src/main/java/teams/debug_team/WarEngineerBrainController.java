package teams.debug_team;

import edu.warbot.brains.brains.WarEngineerBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarEngineerBrainController extends WarEngineerBrain
{
    public WarEngineerBrainController()
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
