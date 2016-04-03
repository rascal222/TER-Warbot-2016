package teams.debug_team;

import edu.warbot.brains.brains.WarKamikazeBrain;
import edu.warbot.communications.WarMessage;

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
