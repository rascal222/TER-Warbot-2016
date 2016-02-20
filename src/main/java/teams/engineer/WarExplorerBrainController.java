package teams.engineer;

import edu.warbot.agents.agents.WarEngineer;
import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarExplorerBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarExplorerBrainController extends WarExplorerBrain {

    @SuppressWarnings("unused")
	private boolean _starving;

    public WarExplorerBrainController() {
        super();

        _starving = false;
    }

    @Override
    public String action() {
        List<WarAgentPercept> percepts = getPercepts();

        for (WarAgentPercept p : percepts) {
            switch (p.getType()) {
                case WarFood:
                    if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagFull()) {
                        setHeading(p.getAngle());
                        return WarExplorer.ACTION_TAKE;
                    } else if (!isBagFull()) {
                        setHeading(p.getAngle());
                    }
                    break;
                case WarBase:
                    if (isEnemy(p)) {
                        broadcastMessageToAll("Enemy base on sight", String.valueOf(p.getAngle()), String.valueOf(p.getDistance()));
                    }
                    break;
                case WarEngineer:
                    if (p.getDistance() < WarEngineer.MAX_DISTANCE_GIVE && getNbElementsInBag() > 0) {
                        setDebugString("Giving food");
                        setIdNextAgentToGive(p.getID());
                        return WarExplorer.ACTION_GIVE;
                    }
                    if (isBagEmpty()) {
                        setDebugString("Searching food");
                        if (isBlocked())
                            setRandomHeading();
                        return WarExplorer.ACTION_MOVE;
                    }
                    break;
                default:
                    break;
            }
        }

        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("Need food")) {
                if (!isBagEmpty()) {
                    setHeading(msg.getAngle());
                    return WarExplorer.ACTION_MOVE;
                } else {
                    if (isBlocked())
                        setRandomHeading();
                    return WarExplorer.ACTION_MOVE;
                }
            }
            if (msg.getMessage().equals("Don't need food anymore")) {
                if (isBlocked())
                    setRandomHeading();
                return WarExplorer.ACTION_MOVE;
            }
        }
        if (isBlocked())
            setRandomHeading();
        return WarExplorer.ACTION_MOVE;
    }

}
