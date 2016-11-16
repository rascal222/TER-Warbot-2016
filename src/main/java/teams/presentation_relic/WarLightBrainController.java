package teams.presentation_relic;

import edu.warbot.brains.brains.WarLightBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarLightBrainController extends WarLightBrain
{
    public WarLightBrainController()
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
