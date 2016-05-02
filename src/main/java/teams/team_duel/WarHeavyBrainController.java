package teams.team_duel;

import edu.warbot.brains.brains.WarHeavyBrain;
import edu.warbot.communications.WarMessage;

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