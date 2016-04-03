package teams.debug_team;

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
        broadcastMessageToAll("", "");
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
