package teams.team_duel;

import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarExplorerBrain;

public abstract class WarExplorerBrainController extends WarExplorerBrain {

	private int seek = 1;
	
    public WarExplorerBrainController() {
        super();

    }

    @Override
    public String action() {

        if (isBlocked()){
        	int random = (int )(Math.random() * 25 + 15);
            setHeading(getHeading()+random);
        }
        
        if(seek == 1){
        for (WarAgentPercept wp : getPercepts()) {

            if (wp.getType().equals(WarAgentType.WarBase) && isEnemy(wp)) {
            	String angle = "" + wp.getAngle();
            	String distance = "" + wp.getDistance();
            	broadcastMessageToAgentType(WarAgentType.WarBase, "Trouve", angle, distance);
            	setDebugString("Trouve angle : " + angle);
            	}
        }
        }
        if(seek == 0){
        	
        }
        
        
        return WarExplorer.ACTION_MOVE;
    }

}
