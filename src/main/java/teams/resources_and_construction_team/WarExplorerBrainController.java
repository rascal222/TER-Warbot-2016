package teams.resources_and_construction_team;

import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarExplorerBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarExplorerBrainController extends WarExplorerBrain {

    @SuppressWarnings("unused")
	private boolean _starving;
    private int idBase;

    public WarExplorerBrainController() {
        super();

        _starving = false;
    }

    @Override
    public String action() {
    	if (isBlocked()) {
    		setRandomHeading();
            return WarExplorer.ACTION_MOVE;
    	}           
    	
        List<WarAgentPercept> percepts = getPercepts();

        for (WarAgentPercept p : percepts) {
        	if (p.getType() == WarAgentType.WarBase) {
        		if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagEmpty()) {
                    setHeading(p.getAngle());
                    this.setIdNextAgentToGive(idBase);
                    return WarExplorer.ACTION_GIVE;
                } else if (!isBagFull()) {
                    setHeading(p.getAngle());
                    return WarExplorer.ACTION_MOVE;
                }
        	}
        	if (p.getType() == WarAgentType.WarFood) {
                if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagFull()) {
                    setHeading(p.getAngle());
                    return WarExplorer.ACTION_TAKE;
                } else if (!isBagFull()) {
                    setHeading(p.getAngle());
                    return WarExplorer.ACTION_MOVE;
                }
            }
        }

        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("I am the base and here is my ID")) {
            	idBase = Integer.parseInt(msg.getContent()[0]);
                if (!isBagEmpty()) {
                    setHeading(msg.getAngle());
                    return WarExplorer.ACTION_MOVE;
                } 
            }
        }
        if (!isBagEmpty()) {
        	broadcastMessageToAll("Give me your ID base", "");
        }
        
        return WarExplorer.ACTION_MOVE;
    }

}
