package teams.timetoexplode;

import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.agents.WarEngineer;
import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarExplorerBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarExplorerBrainController extends WarExplorerBrain {

    private int _idBase;
    private boolean _baseFullLife;
    private Double _positionBase;

    public WarExplorerBrainController() {
        super();

        _idBase = 0;
        _baseFullLife = false;
        _positionBase = 0.0;
    }

    @Override
    public String action() {

        if (_idBase == 0) {
            broadcastMessageToAll("Give me your ID base", "");
        } else {
            sendMessage(_idBase, "Are you full life", "");
        }

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
                    if (!isEnemy(p)) {
                        if (isBagEmpty()) {
                            setRandomHeading();
                            return WarExplorer.ACTION_MOVE;
                        }
                        if (!isBagEmpty()) {
                            if (p.getDistance() < WarBase.MAX_DISTANCE_GIVE) {
                                setIdNextAgentToGive(p.getID());
                                return WarExplorer.ACTION_GIVE;
                            } else {
                                return WarExplorer.ACTION_MOVE;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        if (isBagFull()) {
            if (!_baseFullLife) {
                setHeading(_positionBase);
                return WarEngineer.ACTION_MOVE;
            }
        }

        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("I am the base and here is my ID")) {
                String[] content = msg.getContent();
                _idBase = Integer.parseInt(content[0]);
            }
            if (msg.getMessage().equals("I am full life")) {
                _baseFullLife = true;
            }
            if (msg.getMessage().equals("I am not full life")) {
                _positionBase = msg.getAngle();
                _baseFullLife = false;
            }
            if (msg.getMessage().equals("Need food")) {
                if (!isBagEmpty()) {
                    setHeading(msg.getAngle());
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
