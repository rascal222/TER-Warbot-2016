package teams.presentation_relic;

import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarBaseBrainController extends WarBaseBrain {

    public WarBaseBrainController() {
        super();
    }

    @Override
    public String action() {
    	List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
        	if (msg.getMessage().equals("Give me your ID base")) {
                reply(msg, "I am the base and here is my ID", Integer.toString(getID()));
            }
        }
        
        return idle();
    }

}
